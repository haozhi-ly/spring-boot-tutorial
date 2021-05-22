package com.ly.mq.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

@Data
@AllArgsConstructor
public class Producer {
    private final String namesrvAddr;
    private DefaultMQProducer producer;

    public void syncProducer(String msg) throws Exception {


        //消息创建需要指定topic ，topic底层可和一个或多个队列进行绑定，在topic下，还有tag 相当于一个子topic
        Message message =
                new Message("topic_test","TagA"
                ,msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(message);
        System.out.println(result.toString());
        //producer.shutdown();
    }

    public void asyncProducer(String msg) throws Exception {

        Message message =
                new Message("topic_test","TagA"
                        ,msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("OK %s %s", msg,
                        sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable e) {

            }
        });
        //producer.shutdown();
    }

    public Producer(String namesrvAddr) throws Exception {
        this.namesrvAddr = namesrvAddr;
        producer =
                new DefaultMQProducer("rocketmq_test");
        producer.setNamesrvAddr(namesrvAddr);
        //producer.
        producer.start();
    }

    public void orderProducer() throws Exception {


        for (int i = 0; i < 100; i++) {

            int orderId = i % 10;
            Message message =
                    new Message("topic_test", "TagA"
                            , ("hello ordered message,i:" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    System.out.printf("id:%d,msqs's size:%d,index:%d", id, mqs.size(),index);
                    System.out.println();
                    return mqs.get(index);
                }
            }, orderId);

            System.out.println(result);

            //producer.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        String namesrvAddr = "192.168.48.138:9876";
        Producer producer = new Producer(namesrvAddr);
        producer.orderProducer();
        /*for(int i = 0;i<10;i++){
            producer.syncProducer("hello rocketmq,"+i);
            producer.asyncProducer("hello rocketmq,"+i);

        }*/
    }
}
