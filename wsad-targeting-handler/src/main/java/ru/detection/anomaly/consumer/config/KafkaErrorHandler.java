package ru.detection.anomaly.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Обработчик ошибок
 */
@Component(value = "kafkaErrorHandler")
@Slf4j
public class KafkaErrorHandler implements KafkaListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.error("Ошибка обработки сообщения", exception);
        return null;
    }
}
