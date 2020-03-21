package ai.ssy.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @Description: rabbitMQ连接的配置类
 * @Author:ssy
 * @Date: 2020/3/21
**/
@Component
public class MQClientInfo {
	
	@Value("${spring.rabbitmq.host}")
	public String IP_ADDRESS;
	@Value("${spring.rabbitmq.port}")
	public int PORT;
	@Value("${spring.rabbitmq.username}")
	public String USERNAME;
	@Value("${spring.rabbitmq.password}")
	public String PASSWORD;
	

	public ConnectionFactory factory = new ConnectionFactory();
	public Connection connection = null;

	public Connection getConnection() throws Exception {
		factory.setHost(IP_ADDRESS) ;
		factory.setPort(PORT) ;
		factory.setUsername(USERNAME);
		factory.setPassword(PASSWORD);
		return factory.newConnection();//创建连接
	}
}
