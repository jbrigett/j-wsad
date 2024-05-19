package ru.detection.anomaly.model.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.model.entity.Session;
import ru.detection.anomaly.model.entity.SessionRequest;
import ru.detection.anomaly.model.entity.types.ContentType;
import ru.detection.anomaly.model.entity.types.RequestType;
import ru.detection.anomaly.model.entity.types.ResponseCode;
import ru.detection.anomaly.model.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SessionService {

    private SessionRepository repository;

    public List<Session> getAllTerminatedSession(LocalDateTime endOfSession) {
        return repository.findAllByEndOfSessionBefore(endOfSession);
    }

    public Session findSessionBySessionId(String sessionId) {
        return repository.findBySessionId(sessionId);
    }

    public Session saveSession(Session session) {
        return repository.save(session);
    }

    public void addClick(Session session) {
        session.setClicksNumber(session.getClicksNumber() + 1);
    }

    public void addWeight(Session session, Double weight) {
        session.setWeight(session.getWeight() + weight);
    }

    public void addImageRequest(Session session, SessionRequest request) {
        if (request.getContentType().equals(ContentType.IMAGE)) {
            session.setImageRequests(session.getImageRequests() + 1);
        }
    }

    public void addPdfRequest(Session session, SessionRequest request) {
        if (request.getContentType().equals(ContentType.PDF)) {
            session.setPdfRequests(session.getPdfRequests() + 1);
        }
    }
    public void addRobotRequest(Session session, SessionRequest request) {
        if (request.getContentType().equals(ContentType.ROBOT)) {
            session.setRobotFileRequests(session.getRobotFileRequests() + 1);
        }
    }

    public void addSpecialRequest(Session session, SessionRequest request) {
        if (!(request.getRequestType().equals(RequestType.GET) ||
                request.getRequestType().equals(RequestType.POST))) {
            session.setSpecialRequests(session.getSpecialRequests() + 1);
        }
    }
    public void addErrorRequest(Session session, SessionRequest request) {
        if (request.getCode().equals(ResponseCode.FAIL)) {
            session.setErrorsRequests(session.getErrorsRequests() + 1);
        }
    }

    public void addDeviationOfDeath(Session session, Long deviation) {
        session.setTotalDeviationOfDeath(session.getTotalDeviationOfDeath() + deviation);
    }
}
