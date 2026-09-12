package com.cohkomapa.imageboard.media.service.scheduler;

import com.cohkomapa.imageboard.media.service.media.MediaCleanupService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediaCleanupScheduler {

    private final MediaCleanupService mediaCleanupService;

    @Scheduled(fixedDelayString = "${media.cleanup.fixed-delay}")
    @SchedulerLock(name = "cleanupPendingMedia", lockAtMostFor = "10m")
    public void cleanupPendingMedia() {
        mediaCleanupService.cleanupPendingMedia();
    }
}