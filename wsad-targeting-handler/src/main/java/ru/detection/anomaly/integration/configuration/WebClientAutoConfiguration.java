package ru.detection.anomaly.integration.configuration;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
@EnableConfigurationProperties(WebClientAutoConfiguration.Settings.class)
public class WebClientAutoConfiguration {

    public static final String REST_API_WEB_CLIENT = "restApiWebClient";

    private final Settings settings;

    private final ExchangeStrategies exchangeStrategies;

    public WebClientAutoConfiguration(Settings settings) {
        this.settings = settings;
        this.exchangeStrategies = ExchangeStrategies.builder().build();
    }

    @Bean(REST_API_WEB_CLIENT)
    public WebClient advertisersApiWebClient() {
        var httpClient = HttpClient.create()
                .option(CONNECT_TIMEOUT_MILLIS, settings.connectTimeout)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(settings.readTimeout, MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(settings.writeTimeout, MILLISECONDS)));

        if (settings.wiretapEnabled) {
            httpClient = httpClient.wiretap(WebClientAutoConfiguration.class.getName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        }

        return WebClient.builder()
                .baseUrl(settings.baseUrl)
                .exchangeStrategies(exchangeStrategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Setter
    @NoArgsConstructor
    @ConfigurationProperties(prefix = "rest-api")
    static final class Settings {
        private String baseUrl;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;
        private boolean wiretapEnabled;
    }
}
