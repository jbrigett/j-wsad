package ru.detection.anomaly.rest.service.converter;

import lombok.NoArgsConstructor;
import ru.detection.anomaly.model.entity.Session;
import ru.detection.anomaly.service.dbscan.dto.TerminatedSessionDto;
import ru.detection.anomaly.model.entity.TerminatedSession;

import java.time.Duration;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class toTerminatedSessionConverter {

    public static TerminatedSessionDto toTerminatedSession(Session entity) {
        var clicksNumber = entity.getClicksNumber();
        return TerminatedSessionDto.builder()
                .id(entity.getId())
                .sessionId(entity.getSessionId())
                .clicksNumber(Double.valueOf(entity.getClicksNumber()))
                .weight(entity.getWeight())
                .imagePercentage(getPercentage(entity.getImageRequests(), clicksNumber))
                .pdfPercentage(getPercentage(entity.getPdfRequests(), clicksNumber))
                .errorsPercentage(getPercentage(entity.getErrorsRequests(), clicksNumber))
                .specialRequestPercentage(getPercentage(entity.getSpecialRequests(), clicksNumber))
                .robotFilePercentages(getPercentage(entity.getRobotFileRequests(), clicksNumber))
                .deviationOfDeathPercentage(getPercentage(entity.getTotalDirectRequest(), clicksNumber))
                .directRequestDeviation(getPercentage(entity.getTotalDirectRequest(), clicksNumber))
                .speedOfAccess(getSpeed(clicksNumber, entity.getStartOfSession(), entity.getEndOfSession()))
                .build();
    }

    public static TerminatedSession terminatedSessionDto(TerminatedSession entity) {

    }

    private static Double getPercentage(Long specialRequests, Long totalRequests) {
        return (double)specialRequests / (totalRequests - specialRequests);
    }

    private static Double getSpeed(Long clicksAmount, LocalDateTime startOfSession, LocalDateTime endOfSession) {
        return (double)clicksAmount / Duration.between(startOfSession, endOfSession).toMinutes();
    }

}
