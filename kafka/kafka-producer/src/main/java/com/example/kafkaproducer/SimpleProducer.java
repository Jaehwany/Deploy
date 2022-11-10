package com.example.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class SimpleProducer {
    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "ddokbun.com:8992";

    public static void main(String[] args) {
        String messageValue = "testMessage";
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);
        KafkaProducer<String,String> producer =new KafkaProducer<>(configs);

        // 1. value만 보내기
        ProducerRecord<String,String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
        producer.send(record);

        // 2. value+key 보내기
        ProducerRecord<String,String> record2 = new ProducerRecord<>(TOPIC_NAME, "K1",messageValue);
        producer.send(record2);

        // 3. value+key 지정파티션으로 보내기
        ProducerRecord<String,String> record3 = new ProducerRecord<>(TOPIC_NAME,1, "K2",messageValue);
        producer.send(record3);

        // 4. 특정 key 지정파티션으로 보내기 & 레코드 전송 결과 확인하기
        ProducerRecord<String,String> record4 = new ProducerRecord<>(TOPIC_NAME, "pangyo", "pangyo");
        try {
            RecordMetadata metadata = producer.send(record4).get();
            log.info(metadata.toString());
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        } finally {
            producer.flush();
            // 프로듀서의 안전한 종료
            producer.close();
        }

    }

}
