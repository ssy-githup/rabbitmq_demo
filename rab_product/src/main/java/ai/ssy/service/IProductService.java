package ai.ssy.service;

import ai.ssy.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;


public interface IProductService {
	
	public int updateProduct(Order order) throws Exception;
	public void updateProduct4MQ(Order order, Channel channel, Message message) throws Exception;
}
