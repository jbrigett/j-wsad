package ru.detection.anomaly.integration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.detection.anomaly.integration.dto.SessionRequestResponseDto;

import static ru.detection.anomaly.integration.IntegrationException.ExternalApi.REST_API;
import static ru.detection.anomaly.integration.configuration.WebClientAutoConfiguration.REST_API_WEB_CLIENT;
import static ru.detection.anomaly.integration.WebClientUtils.onServerErrorMax;

@Service
@EnableConfigurationProperties(RestApiService.SearchContentSettings.class)
public class RestApiService {
    private final WebClient webClient;
    private final SearchContentSettings searchSettings;

    public RestApiService(@Qualifier(REST_API_WEB_CLIENT)WebClient webClient, SearchContentSettings searchSettings) {
        this.webClient = webClient;
        this.searchSettings = searchSettings;
    }

    public ResponseEntity<SessionRequestResponseDto> searchContent(SessionRequestResponseDto requestDto) {
        try {
            return webClient.post()
                    .uri(searchSettings.getUrl())
                    .bodyValue(requestDto)
                    .retrieve()
                    .toEntity(SessionRequestResponseDto.class)
                    .retryWhen(onServerErrorMax(searchSettings.getRetryMax()))
                    .block();
        } catch (Exception ex) {
            throw new IntegrationException(REST_API, ex);
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "egress.smartmarket-api.search")
    static final class SearchContentSettings {
        private String url;
        private int retryMax;
    }
}
