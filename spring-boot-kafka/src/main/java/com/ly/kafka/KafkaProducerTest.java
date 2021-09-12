package com.ly.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaProducerTest {
    public  void   send(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.234.8:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 109; i++)
        /*producer.send(new ProducerRecord<>("my-topic", Integer.toString(i),
                    new Bytes(ProtoStuffUtil.serializer(i+""))));*/

        producer.send(new ProducerRecord<>("my-topic", Integer.toString(i),
                i+""));
        producer.close();
    }
}
