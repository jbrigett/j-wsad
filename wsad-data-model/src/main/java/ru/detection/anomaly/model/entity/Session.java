package ru.detection.anomaly.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.detection.anomaly.model.entity.types.SessionType;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    private Long id;
    /**ID of session*/
    private String sessionId;

    /**begin of session*/
    private LocalDateTime startOfSession;
    /**end of session*/
    private LocalDateTime endOfSession;

    /**number of request per session*/
    private Long clicksNumber;
    /**weight of  all requests*/
    private Double weight;

    /**amount of images request*/
    private Long imageRequests;
    /**amount of pdf request*/
    private Long pdfRequests;
    /**amount of failed request*/
    private Long errorsRequests;
    /**amount of special request(not GET or POST)*/
    private Long specialRequests;
    /**amount of robot.txt file requests*/
    private Long robotFileRequests;

    /**total deviation from all requests*/
    private Long totalDeviationOfDeath;
    /**total amount of direct requests*/
    private Long totalDirectRequest;

    /**to check direct requests percentage*/
    private String lastRequestLink;

    /**last modification time to check by timeout*/
    private LocalDateTime lastModification;

    /**changed after termination*/
    private boolean isChanged;
    /**is terminated*/
    private boolean isTerminated;

    /**type of session after proceeding*/
    private SessionType sessionType;
}
