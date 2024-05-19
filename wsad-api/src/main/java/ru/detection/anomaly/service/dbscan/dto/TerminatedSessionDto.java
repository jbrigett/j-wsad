package ru.detection.anomaly.service.dbscan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TerminatedSessionDto {
    private Long id;
    /**ID of session*/
    private String sessionId;
    /**begin of session*/
    private LocalDateTime startOfSession;
    /**end of session*/
    private LocalDateTime endOfSession;

    /**number of request per session*/
    private Double clicksNumber;
    /**weight of  all requests*/
    private Double weight;

    /**percentage of images request*/
    private Double imagePercentage;
    /**percentage of pdf request*/
    private Double pdfPercentage;
    /**percentage of failed request*/
    private Double errorsPercentage;
    /**percentage of special request(not GET or POST)*/
    private Double specialRequestPercentage;
    /**total amount of robot.txt file requests*/
    private Double robotFilePercentages;

    /**total deviation from all requests*/
    private Double deviationOfDeathPercentage;
    /**total amount of direct requests*/
    private Double directRequestDeviation;

    /**speed of user's session*/
    private Double speedOfAccess;

}
