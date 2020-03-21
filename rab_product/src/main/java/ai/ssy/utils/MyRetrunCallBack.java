package ai.ssy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


public class MyRetrunCallBack implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("correlationId:"+message.getMessageProperties().getCorrelationId());
        System.out.println("replyText:"+replyText);
        System.out.println("replyCode:"+replyCode);
        System.out.println("交换机:{}"+exchange);
        System.out.println("routingKey:"+routingKey);
        System.out.println("表示消息记录为不可达");
    }
}
