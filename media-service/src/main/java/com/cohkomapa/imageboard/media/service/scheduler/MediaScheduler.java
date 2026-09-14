package com.cohkomapa.imageboard.media.service.scheduler;

import com.cohkomapa.imageboard.media.service.media.MediaCleanupService;
import com.cohkomapa.imageboard.media.service.media.MediaDeletionService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediaScheduler {

    private final MediaCleanupService mediaCleanupService;
    private final MediaDeletionService mediaDeletionService;

    @Scheduled(fixedDelayString = "${media.cleanup.fixed-delay}")
    @SchedulerLock(name = "cleanupPendingMedia", lockAtMostFor = "10m")
    public void cleanupPendingMedia() {
        mediaCleanupService.cleanupPendingMedia();
    }

    @Scheduled(fixedDelayString = "${media.deleting.fixed-delay}")
    @SchedulerLock(name = "deleteMarkedMedia", lockAtMostFor = "10m")
    public void deleteMarkedMedia() {
        mediaDeletionService.deleteMarkedMedia();
    }
}