package com.john;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

class ConsumerContainer {
    Properties properties;

    {
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "165.227.99.49:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "fraud_detection");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    }

    TransferLog log = new TransferLog();
    private static final ConsumerContainer consumerContainer = new ConsumerContainer();
    Consumer<String, String> consumer = new KafkaConsumer<>(properties);


    public static ConsumerContainer getConsumer() {
        return consumerContainer;
    }

    public void run() {
        consumer.subscribe(Collections.singleton("cash_out"));
//        consumer.subscribe(Collections.singleton("transfer"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
                switch (TransactionUtility.getTopic(record.value())) {
                    case "transfer":
                        log.add(record.value());
                        System.out.println("t");
                        break;
                    case "cash_out":
                        if (log.check(record.value())) {
                            System.out.println("Potential Fraud");
                        }
                        System.out.println("c");
                        System.out.println(record.value());
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
