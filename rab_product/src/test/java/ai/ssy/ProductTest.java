package ai.ssy;

import ai.ssy.entity.Order;
import ai.ssy.service.IProductService;
import ai.ssy.utils.MyConfirmCallBack;
import ai.ssy.utils.MyRetrunCallBack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @Description: TODO
 * @Author: ssy
 * @Date: 2020/3/21
**/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ProductApplication.class)
public class ProductTest {

	@Autowired
	private IProductService productService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void updateProduct() throws Exception {
		Order order = new Order();
		order.setProductId(1006);
		order.setCustomer("123465");
		order.setNumber(1);
		productService.updateProduct(order);
	}


	@Test
	public void sendString() throws Exception {
		//使用rabbitTemplate 发送消息
		//字符串类型

		//开启确认模式
		rabbitTemplate.setConfirmCallback(new MyConfirmCallBack());

		//开启消息可达监听
		rabbitTemplate.setReturnCallback(new MyRetrunCallBack());

		//rabbitTemplate.convertAndSend("testExchange","testRollingKey","你好的！");
		rabbitTemplate.convertAndSend("1111","你好的！");
		//Mapl类型
	}
}
