package ru.detection.anomaly.integration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.detection.anomaly.integration.dto.Response;

import java.util.List;

public class IntegrationException extends RuntimeException {

    @Getter
    private final ExternalApi externalApi;

    public IntegrationException(ExternalApi system, String msg) {
        super(msg);
        this.externalApi = system;
    }

    public IntegrationException(ExternalApi system, Exception e) {
        super(e.getMessage(), e);
        this.externalApi = system;
    }

    public IntegrationException(ExternalApi system, String msg, List<Response.RestError> errors) {
        super(formMessage(msg, errors));
        this.externalApi = system;
    }

    private static String formMessage(String msg, List<Response.RestError> errors) {
        return errors != null
                ? msg + "\n" + errors
                : msg;
    }

    @RequiredArgsConstructor
    public enum ExternalApi {
        REST_API("rest-api")
        ;

        @Getter
        private final String description;
    }
}
