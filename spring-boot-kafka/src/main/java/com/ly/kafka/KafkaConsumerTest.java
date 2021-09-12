package com.ly.kafka;

import com.ly.ProtoStuffUtil;
import com.ly.service.ImportTest2Service;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class KafkaConsumerTest {

    private Logger logger = LoggerFactory.getLogger(ImportTest2Service.class);

    public void consumer() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.234.8:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.BytesDeserializer");
        KafkaConsumer<String, Bytes> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("my-topic"));
        while (true) {
            ConsumerRecords<String, Bytes> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Bytes> record : records)
                try {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
                            ProtoStuffUtil.deserializer(record.value().get(), String.class));

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public void manuallyConsumer() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.234.8:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "false");
        //props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("max.poll.records", "1");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("offset-test", "my-topic"));
        AtomicInteger atomicInteger = new AtomicInteger();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                if (records.isEmpty()) {
                    break;
                }

                logger.warn("offset-test = {}, key = {}, value = {}", record.offset(), record.key(), record.value());

                if (atomicInteger.get() % 10 == 0) {
                    consumer.commitAsync();
                }
                atomicInteger.incrementAndGet();
            }
        }
    }
}
