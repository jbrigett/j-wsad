package ru.detection.anomaly.integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @Setter(AccessLevel.NONE)
    private int statusCode;

    private List<RestError> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(of = {"desc"})
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RestError {

        private String desc;
    }
}
