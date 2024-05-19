package ru.detection.anomaly.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.detection.anomaly.model.entity.types.ContentType;
import ru.detection.anomaly.model.entity.types.RequestType;
import ru.detection.anomaly.model.entity.types.ResponseCode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {
    private String sessionId;
    private ResponseCode code;
    private RequestType requestType;
    private ContentType contentType;
    private Double size;
    private LocalDateTime date;
    private String link;
}
