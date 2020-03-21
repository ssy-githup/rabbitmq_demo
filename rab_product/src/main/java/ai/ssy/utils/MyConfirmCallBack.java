package ai.ssy.utils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;


/**
 * @Description: TODO
 * @Author: Administrator
 * @Date: 2020/3/21
**/
public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("correlationData:========>{},ack的标志{}"+correlationData.getId()+"/r/n"+ack);
        if(ack) {
            System.out.println("mq生产端消息已经成功投递到了broker,更新我们消息日志表");
        }else {
            System.out.println("mq生产端没有被broker ack,原因:{}"+cause);
        }
    }
}
