package com.ly.mq.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Data
@AllArgsConstructor
public class Consumer {
    private final String namesrvAddr;

    public void consumer() throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_test_group_1");
        consumer.setNamesrvAddr(namesrvAddr);

        //tag支持 || 分割订阅多个tag,或者以*为通配符
        consumer.subscribe("topic_test","*");
        //consumer.setConsumeThreadMin();
        consumer.setMessageModel(MessageModel.CLUSTERING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt ext = msgs.get(0);
                try {
                    String content = new String(ext.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    System.out.println(String.format("msgid:%s,msgcontent:%s",ext.getMsgId(),content));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();


        Thread.sleep(600000);
    }

    public void consumerOrderly() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_test_group_1");
        consumer.setNamesrvAddr(namesrvAddr);

        consumer.subscribe("topic_test","TagA");

        //consumer.setConsumeThreadMin();

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {

                MessageExt messageExt = msgs.get(0);
                try {

                    String content = new String(messageExt.getBody(),RemotingHelper.DEFAULT_CHARSET);
                    System.out.println("thread -number:"+Thread.currentThread().getId()+","
                    +"msgId:"+messageExt.getMsgId()+",content:"+content+",queueId:"+context.getMessageQueue().getQueueId());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();


        Thread.sleep(600000);
    }


    public static void main(String[] args) throws Exception {
        String namesrvAddr = "192.168.48.138:9876";
        Consumer consumer = new Consumer(namesrvAddr);
        //consumer.consumer();
        consumer.consumerOrderly();
    }
}
