package ru.detection.anomaly.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.detection.anomaly.model.entity.Session;
import ru.detection.anomaly.model.entity.types.SessionType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findAllByEndOfSessionBefore(LocalDateTime endOfSession);
    List<Session> findAllByTerminatedAndSessionType(boolean terminated, SessionType sessionType);
    Session findBySessionId(String sessionId);
}
