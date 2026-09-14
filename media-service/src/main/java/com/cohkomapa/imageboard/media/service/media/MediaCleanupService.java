package com.cohkomapa.imageboard.media.service.media;

import com.cohkomapa.imageboard.media.enums.MediaStatus;
import com.cohkomapa.imageboard.media.repository.MediaRepository;
import com.cohkomapa.imageboard.media.service.objectstorage.ObjectStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MediaCleanupService {

    private final MediaCleanupService self;
    private final MediaService mediaService;
    private final ObjectStorageService objectStorageService;
    private final MediaRepository mediaRepository;

    @Value("${media.cleanup.pending-ttl}")
    private Duration pendingTtl;

    private static final int CLEANUP_BATCH_SIZE = 100;

    public MediaCleanupService(
            @Lazy MediaCleanupService self,
            MediaService mediaService,
            ObjectStorageService objectStorageService,
            MediaRepository mediaRepository
    ) {
        this.self = self;
        this.mediaService = mediaService;
        this.objectStorageService = objectStorageService;
        this.mediaRepository = mediaRepository;
    }

    public void cleanupPendingMedia() {
        ZonedDateTime pendingCreatedBefore = ZonedDateTime.now().minus(pendingTtl);

        while (true) {
            List<UUID> mediaToDeleteIds = self.getMediaToDeleteIds(pendingCreatedBefore);

            for (UUID mediaToDeleteId : mediaToDeleteIds) {
                objectStorageService.deleteByPrefix(mediaToDeleteId + "/");
            }
            if (mediaToDeleteIds.isEmpty()) {
                break;
            }
            mediaService.deleteAllByIds(mediaToDeleteIds);
        }
    }

    @Transactional(readOnly = true)
    public List<UUID> getMediaToDeleteIds(ZonedDateTime pendingCreatedBefore) {
        Pageable cleanupBtach = PageRequest.of(0, CLEANUP_BATCH_SIZE); // Всегда получаем первые N элементов выборки
        return mediaRepository.findIdsByStatusAndCreatedBefore(
                MediaStatus.PENDING,
                pendingCreatedBefore,
                cleanupBtach
        );
    }
}