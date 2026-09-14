package com.cohkomapa.imageboard.media.service.media;

import com.cohkomapa.imageboard.media.repository.MediaRepository;
import com.cohkomapa.imageboard.media.service.objectstorage.ObjectStorageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MediaDeletionService {

    private final MediaDeletionService self;
    private final MediaService mediaService;
    private final ObjectStorageService objectStorageService;

    private final MediaRepository mediaRepository;

    private static final int DELETE_BATCH_SIZE = 100;

    public MediaDeletionService(
            @Lazy MediaDeletionService self,
            MediaService mediaService,
            ObjectStorageService objectStorageService,
            MediaRepository mediaRepository
    ) {
        this.self = self;
        this.mediaService = mediaService;
        this.objectStorageService = objectStorageService;
        this.mediaRepository = mediaRepository;
    }

    public void deleteMarkedMedia() {
        while (true) {
            List<UUID> mediaMarkedToDeleteIds = self.getMediaMarkedToDeleteIds();
            for (UUID mediaId : mediaMarkedToDeleteIds) {
                objectStorageService.deleteByPrefix(mediaId + "/");
            }
            if (mediaMarkedToDeleteIds.isEmpty()) {
                break;
            }
            mediaService.deleteAllByIds(mediaMarkedToDeleteIds);
        }
    }

    @Transactional(readOnly = true)
    public List<UUID> getMediaMarkedToDeleteIds() {
        Pageable deleteBatch = PageRequest.of(0, DELETE_BATCH_SIZE);
        return mediaRepository.findIdsMarkedToDelete(deleteBatch);
    }
}