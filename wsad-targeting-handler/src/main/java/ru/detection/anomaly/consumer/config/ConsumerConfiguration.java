package ru.detection.anomaly.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.RetryingBatchErrorHandler;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class ConsumerConfiguration {

    @Value("${spring.transaction.batch-processing-error-timeout-ms:10000}")
    private int backoffInterval;
    @Value("${spring.transaction.batch-processing-max-attempts:6}")
    private int maxAttempts;

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(ConsumerFactory<Object, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<?, ?> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        factory.setBatchErrorHandler(batchErrorHandler());
        return factory;
    }

    @Bean
    public JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }


    @Bean
    public RetryingBatchErrorHandler batchErrorHandler() {
        return new RetryingBatchErrorHandler(
                new FixedBackOff(backoffInterval, maxAttempts),
                (consumerRecord, e) -> log.error("Не удалось обработать батч.", e));
    }
}
