package ru.detection.anomaly.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import ru.detection.anomaly.model.entity.Session;
import ru.detection.anomaly.model.service.SessionService;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "scheduler.jobs.clear-old-sessions.enabled", havingValue = "true", matchIfMissing = true)
public class DBCleanScheduler {

    private SessionService sessionService;

    @Scheduled(cron = "${scheduler.jobs.find-terminated-sessions..cron}")
    @SchedulerLock(
            name = "clear-db",
            lockAtMostFor = "${scheduler.jobs.clear-old-sessions.shedlock.at-most}",
            lockAtLeastFor = "${scheduler.jobs.clear-old-sessions.shedlock.at-least}"
    )
    public void launchDBCleanJob() {
        LockAssert.assertLocked();

        log.info("Запущена фоновая задача поиска завершенных сессий");
        List<Session> terminatedSessions = sessionService.getAllTerminatedSession();
        if (!(terminatedSessions == null || terminatedSessions.isEmpty())) {
            //TODO do here update of sessions
        }
        log.info("Фоновая задача поиска завершенных сессий заверешена");
    }}
