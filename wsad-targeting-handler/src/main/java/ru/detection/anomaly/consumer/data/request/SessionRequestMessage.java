package ru.detection.anomaly.consumer.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestMessage {
    private SessionRequestPayload payload;
    private String loading_id; //NOSONAR
    private String message_id; //NOSONAR
    private String hash;
}
