package ru.detection.anomaly.integration.dto;

import lombok.Builder;
import lombok.Data;
import ru.detection.anomaly.model.entity.types.ContentType;
import ru.detection.anomaly.model.entity.types.RequestType;
import ru.detection.anomaly.model.entity.types.ResponseCode;

import java.time.LocalDateTime;

@Data
@Builder
public class SessionRequestDto {

    private String sessionId;
    private ResponseCode code;
    private RequestType requestType;
    private ContentType contentType;
    private Double size;
    private LocalDateTime date;
    private String link;
}
