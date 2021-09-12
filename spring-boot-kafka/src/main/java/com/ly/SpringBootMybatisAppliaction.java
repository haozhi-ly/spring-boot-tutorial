package com.ly;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, MybatisAutoConfiguration.class})
@EnableScheduling
public class SpringBootMybatisAppliaction extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootMybatisAppliaction.class);
	}

	@KafkaListener(groupId = "test1",concurrency ="3",topics = {"my-topic"})
	public void listen(List<ConsumerRecord<String, String>> list, Acknowledgment ack){
		for (ConsumerRecord<String,String> record: list){
			try {
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
						record.value());
				ack.acknowledge();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@KafkaListener(groupId = "test2",concurrency ="3",topics = {"my-topic"})
	public void listen1(List<ConsumerRecord<String, String>> list, Acknowledgment ack){
		for (ConsumerRecord<String,String> record: list){
			try {
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(),
						record.value());
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			//ack.
		}
	}

	@Bean("kafkaListenerContainerFactory-test1")
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
	kafkaListenerContainerFactory(ConsumerFactory consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		//consumerFactory.getConfigurationProperties().put("")
		return factory;
	}

	@Bean("kafkaListenerContainerFactory-test2")
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
	kafkaListenerContainerFactory2(ConsumerFactory consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		//consumerFactory.getConfigurationProperties().put("")
		return factory;
	}

	/*@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());

		return props;
	}*/
	
	public static void main(String[] args){
		SpringApplication.run(SpringBootMybatisAppliaction.class, args);
	}
}
