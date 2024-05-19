package ru.detection.anomaly.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.detection.anomaly.model.entity.TerminatedSession;

public interface TerminatedSessionRepository extends JpaRepository<TerminatedSession, Long> {
}
