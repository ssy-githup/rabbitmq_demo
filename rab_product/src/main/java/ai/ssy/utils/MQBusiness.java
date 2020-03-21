package ai.ssy.utils;

import ai.ssy.entity.Order;
import ai.ssy.service.IProductService;
import ai.ssy.util.MQProperties;
import com.rabbitmq.client.Channel;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Description: TODO
 * @Author: Administrator
 * @Date: 2020/3/21
**/
@Component
@RabbitListener(queues= MQProperties.QUEUE_NAME_TX)
public class MQBusiness {

	@Autowired
	private IProductService productService;
	
	@RabbitHandler
	public void process(String body, Channel channel, Message message) throws Exception {
		JSONObject obj = new JSONObject(body);
		int orderId = obj.getInt("orderId");
		int productId = obj.getInt("productId");
		int number = obj.getInt("number");
		Order order = new Order();
		order.setOrderId(orderId);
		order.setProductId(productId);
		order.setNumber(number);
		productService.updateProduct4MQ(order, channel, message);
	}

}
