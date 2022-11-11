package com.example.kafkaconsumer.exercise;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class Consumer {
    private final static String TOPIC_NAME = "hello.kafka";
    private final static String BOOTSTRAP_SERVERS = "ddokbun.com:8992";
    private final static String GROUP_ID = "test-group";

    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        while(true){
            ConsumerRecords<String,String> records= consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String,String> record : records){
                log.info("record:{}",record);
            }
            // 비동기 오프셋 커밋 컨슈머 + 비동기 오프셋 커밋 콜백
            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
                    if(e != null)
                        System.out.println("Commit failed");
                    else
                        System.out.println("Commit Successed");
                    if(e != null)
                        log.info("Commit failed for offsets {}",offsets, e);
                }
            });
        }

        // 동기 오프셋 커밋 컨슈머
//        while(true){
//            ConsumerRecords<String,String> records= consumer.poll(Duration.ofSeconds(1));
//            for (ConsumerRecord<String,String> record : records){
//                log.info("record:{}",record);
//            }
//            // 동기 오프셋 커밋 컨슈머
//            consumer.commitSync();
//        }

        // (레코드 단위) 동기 오프셋 커밋 컨슈머 - 특수 상황에 씀
//        while(true){
//            ConsumerRecords<String,String> records= consumer.poll(Duration.ofSeconds(1));
//            Map<TopicPartition, OffsetAndMetadata> currentOffset = new HashMap<>();
//            for (ConsumerRecord<String,String> record : records){
//                log.info("record:{}",record);
//                currentOffset.put(new TopicPartition(record.topic(), record.partition()),new OffsetAndMetadata(record.offset() +1,null));
//                // 동기 오프셋 커밋 컨슈머 (레코드 단위)
//                consumer.commitSync(currentOffset );
//            }
//        }

    }
}
