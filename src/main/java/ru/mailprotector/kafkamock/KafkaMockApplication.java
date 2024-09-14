package ru.mailprotector.kafkamock;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class KafkaMockApplication {
	public static Producer<String, String> producer = null;

	public static void SetupKafka(String host){
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// Create the producer
		producer = new KafkaProducer<>(props);
	}

	public static void main(String[] args) {
		if (args.length == 0){
			System.out.println("No host (in format of 0.0.0.0:00000) specified! Quitting");
			return;
		}
		SetupKafka(args[0]);
		SpringApplication.run(KafkaMockApplication.class, args);
	}

}
