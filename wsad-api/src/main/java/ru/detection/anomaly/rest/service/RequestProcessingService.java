package ru.detection.anomaly.rest.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.detection.anomaly.model.entity.Session;
import ru.detection.anomaly.model.entity.SessionRequest;
import ru.detection.anomaly.model.entity.types.RequestType;
import ru.detection.anomaly.model.entity.types.ResponseCode;
import ru.detection.anomaly.model.service.SessionService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.detection.anomaly.model.entity.types.ContentType.*;

@Slf4j
@Service
public class RequestProcessingService {

    private SessionService service;

    private static final Pattern DEVIATION_PATTERN = Pattern.compile("((https|http):\\/\\/)(.+)");
    /**
     * Check Session ID of given request. Create new entity if there's no entity with same ID
     * or update existing
     * @param request from kafka
     */
    public void processRequest(SessionRequest request) {
        Session session = service.findSessionBySessionId(request.getSessionId());
        if (session != null) {
            updateSession(request, session);
        } else {
            makeNewSession(request);
        }
    }

    public void makeNewSession(SessionRequest request) {
        var newSession = Session.builder()
                .sessionId(request.getSessionId())
                .startOfSession(request.getDate())
                .clicksNumber(1L)
                .weight(request.getSize())
                .imageRequests(request.getContentType().equals(IMAGE) ? 1L : 0L)
                .pdfRequests(request.getContentType().equals(PDF) ? 1L : 0L)
                .errorsRequests(request.getCode().equals(ResponseCode.FAIL) ? 1L : 0L)
                .specialRequests(request.getRequestType().equals(RequestType.GET) || request.getRequestType().equals(RequestType.POST) ? 0L : 1L)
                .robotFileRequests(request.getContentType().equals(ROBOT) ? 1L : 0L)
                .totalDirectRequest(0L)
                .lastRequestLink(request.getLink())
                .totalDeviationOfDeath((long)getDeviation(request.getLink()))
                .isChanged(false)
                .lastModification(request.getDate())
                .build();

        service.saveSession(newSession);
    }

    public void updateSession(SessionRequest request, Session session) {
        service.addClick(session);
        service.addWeight(session, request.getSize());
        service.addImageRequest(session, request);
        service.addErrorRequest(session, request);
        service.addPdfRequest(session, request);
        service.addRobotRequest(session, request);
        service.addSpecialRequest(session, request);
        addDirectRequest(session, request);
        service.addDeviationOfDeath(session, (long)getDeviation(request.getLink()));
        session.setChanged(session.getEndOfSession() != null);
        session.setLastModification(request.getDate());
        session.setLastRequestLink(request.getLink());

        service.saveSession(session);
    }

    /**
     * Getting total deviation in current request
     * @param link from request
     * @return number of deviation
     */
    private int getDeviation(String link) {
        Matcher matcher = DEVIATION_PATTERN.matcher(link);
        String pureLink = matcher.group(3);

        return pureLink == null || pureLink.isEmpty() ? 0 :
                StringUtils.countMatches(pureLink, '/') - 1;
    }

    /**
     *
     * @param link from request
     * @param previousLink from previously saved entity
     * @return true if request is direct
     */
    private boolean isDirect(String link, String previousLink) {
        var diff = StringUtils.difference(link, previousLink);
        return StringUtils.countMatches(diff, '/') <= 1;
    }

    private void addDirectRequest(Session session, SessionRequest request) {
        if (isDirect(request.getLink(), session.getLastRequestLink())) {
            session.setTotalDirectRequest(session.getTotalDirectRequest() + 1);
        }
    }
}
