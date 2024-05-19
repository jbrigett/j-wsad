package ru.detection.anomaly.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sberbank.sm.smm.data.model.entity.personalization.ClientPersonalization;
import ru.sberbank.sm.smm.data.model.repository.ClientPersonalizationRepository;
import ru.sberbank.sm.smm.targetinghandler.data.model.message.client.ClientPersonalizationMessage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalizationConsumer {

    private final Converter<ClientPersonalizationMessage, ClientPersonalization> clientPersonalizationConverter;

    @KafkaListener(topics = "${spring.kafka.consumer.topic-client-recommendation}", clientIdPrefix = "client-ru.sberbank.sm.smm.data.personalization-consumer")
    public void clientPersonalizationListener(List<ClientPersonalizationMessage> personalization) {
        log.info("Из Kafka получен batch персонализации клиентов, размер: {}", personalization.size());
        List<ClientPersonalization> clientPersonalizations = personalization.stream()
                .map(clientPersonalizationConverter::convert)
                .collect(Collectors.toList());
        clientPersonalizationRepository.saveAll(clientPersonalizations);
        log.info("Batch персонализации клиентов успешно обработан");
    }

}
