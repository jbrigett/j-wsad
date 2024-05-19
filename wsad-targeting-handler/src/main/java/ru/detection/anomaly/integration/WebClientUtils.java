package ru.detection.anomaly.integration;

import lombok.NoArgsConstructor;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class WebClientUtils {

    private static final String DEFAULT_MESSAGE = "Сервису не удалось обработать запрос за максимальное количество попыток";

    /**
     * Стратегия повторных запросов в случае получения HTTP кода ответа от сервиса 5xx.
     * @param maxAttempts максимальное кол-во попыток повторного вызова
     */
    public static Retry onServerErrorMax(int maxAttempts) {
        return Retry.max(maxAttempts)
                .filter(WebClientUtils::is5xxServerError)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    Throwable failure = retrySignal.failure();
                    throw new RuntimeException(getMessage(failure), failure);
                });
    }

    private static boolean is5xxServerError(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }

    private static String getMessage(Throwable failure) {
        if (failure instanceof WebClientResponseException) {
            WebClientResponseException exception = (WebClientResponseException) failure;
            String status = exception.getStatusText();
            int code = exception.getRawStatusCode();
            return String.format("%d %s", code, status);
        }

        Throwable failureCause = NestedExceptionUtils.getMostSpecificCause(failure);

        return !ObjectUtils.isEmpty(failureCause.getMessage()) ? failureCause.getMessage() : DEFAULT_MESSAGE;
    }
}
