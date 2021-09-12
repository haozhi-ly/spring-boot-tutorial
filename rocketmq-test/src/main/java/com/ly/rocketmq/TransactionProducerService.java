package com.ly.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

//@Service
public class TransactionProducerService {

    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer_test");
        producer.setNamesrvAddr("localhost:9876");
        producer.setTransactionListener(new TransactionListenerImpl());
        producer.start();


        DefaultMQProducer producer1 = new DefaultMQProducer("producer", RPCHook );
        producer1.setRetryTimesWhenSendFailed(1);
        //producer1

        for (int i=401;i<=500;i++){
            Message message = new Message("transaction_topic1","tag1",
                    String.valueOf(i),String.valueOf(i).getBytes());
            SendResult result = producer.sendMessageInTransaction(message,null);
            System.out.println("the send result :" + result.toString());
            Thread.sleep(10);
        }
        //producer.shutdown();
        System.out.println("send over...");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();


        /*DefaultMQProducer producer1 = new DefaultMQProducer("test");
        producer1.setNamesrvAddr("localhost:9876");
        //producer.setTransactionListener(new TransactionListenerImpl());
        producer1.start();

        for (int i=0;i<=100;i++){
            Message message = new Message("topic1","tag1",
                    String.valueOf(i),String.valueOf(i).getBytes());
            SendResult result = producer1.send(message);
            System.out.println("the send result :" + result.toString());
            Thread.sleep(10);
        }
        producer1.shutdown();
        System.out.println("send over...");*/
    }


    static class TransactionListenerImpl implements TransactionListener{
        AtomicInteger atomicInteger = new AtomicInteger();


        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object o) {

            String msgContent = "";
            try {
                msgContent = new String(message.getBody(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            int tempNum = Integer.parseInt(msgContent);
            if(tempNum %2 == 0){
                return LocalTransactionState.COMMIT_MESSAGE;
            }else{
                return LocalTransactionState.UNKNOW;
            }
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            String msgContent = "";
            try {
                msgContent = new String(messageExt.getBody(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            int tempNum = Integer.parseInt(msgContent);
            System.out.println("the message is checked with msgContent："+tempNum);

            if(tempNum % 25 == 0){
                return LocalTransactionState.COMMIT_MESSAGE;
            }else if(tempNum % 5 == 0){
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            return LocalTransactionState.UNKNOW;
        }
    }
}


