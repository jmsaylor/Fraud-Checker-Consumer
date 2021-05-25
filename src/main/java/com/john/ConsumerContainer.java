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
        consumer.subscribe(Collections.singleton("fraud_detection"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(400));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
                if (record.value().length() < 25) continue;
                if (TransactionUtility.isTransfer(record.value())) {
                    System.out.println("transfer");
                    log.add(record.value());
                } else {
                    System.out.println("cash_out");
                    try {
                        if (log.check(TransactionUtility.getAmount(record.value()))) {
                            System.out.print("FRAUD ALERT " + record.value());
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR");
                    }
                }

            }
        }
    }

}
