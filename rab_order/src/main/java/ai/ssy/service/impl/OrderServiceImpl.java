package ai.ssy.service.impl;

import ai.ssy.entity.Order;
import ai.ssy.mapper.OrderMapper;
import ai.ssy.service.IOrderService;
import ai.ssy.util.MQProperties;
import ai.ssy.utils.MQClientInfo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


/**
 * @Description: TODO
 * @Author: ssy
 * @Date: 2020/3/21
**/
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private MQClientInfo mqInfo;
	
	private RestTemplate template = new RestTemplate();


	/**
	 * @Description: TODO
	 * @Author:测试消费者接受消息
	 * @Date: 2020/3/21
	**/
	@RabbitListener(queues = {"testMessage"})
	public void consumerMsg(Message message, Channel channel) throws IOException {
		System.out.println(Thread.currentThread().getName() + " myQueueTest：");
		System.out.println("监听tulingBootQueue消费消息=======:"+new String(message.getBody()));
		//手工签收
		Long deliveryTag = (Long) message.getMessageProperties().getDeliveryTag();
		channel.basicAck(deliveryTag,false);
	}

	/**
	 * 客户下单
	 **/
	@Transactional
	public int shopping(Order order) throws Exception {
		System.out.println("============创建订单 ");
		int row = orderMapper.insert(order); //下单，插入Order表
		
		if(row>0){
			//通过接口调用库存系统，修改库存。
			String url = "http://localhost:8082/product/updateProduct?productId="+order.getProductId()+"&number="+order.getNumber();
			String result = template.getForEntity(url, String.class).getBody();
			System.out.println(result);
		}
		return row;
	}
	
	@Transactional
	public int shopping4MQ(Order order) throws Exception {
		System.out.println("============创建订单");
		int row = orderMapper.insert(order); //下单，插入Order表
		
		if(row>0){
			Channel channel = mqInfo.getConnection().createChannel();
			try {
				/**
				 * 开启MQ事务 保证了此次事务和消息的统一
				 */
				channel.txSelect(); //开启MQ事务   或者使用Confirm机制
				//下单成功后，将订单信息保存至RabbitMQ中，由库存系统从MQ中获取数据，修改库存。
				channel.basicPublish(MQProperties.EXCHANGE_NAME_TX, MQProperties.ROUTE_KEY_TX, MessageProperties.PERSISTENT_TEXT_PLAIN, order.toString().getBytes()); //发送消息
				//...
				channel.txCommit(); //提交MQ事务   
			} catch (Exception e) {
				//MQ消息发送失败，回滚事务
				channel.txRollback();
				throw e; //继续抛出异常，回滚本地事务
			}
		}
		return 0;
	}
	
	@Transactional
	public void shoppingRollback(int orderId) throws Exception {
		System.out.println("============删除订单");
		orderMapper.delete(orderId);
	}

	@Override
	public void shoppingCommit(int orderId) throws Exception {
		System.out.println("============业务处理成功，修改订单状态");
		orderMapper.update(orderId);
	}


}
