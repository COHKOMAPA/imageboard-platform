package com.cohkomapa.imageboard.media.service.media;

import com.cohkomapa.imageboard.media.dto.response.MediaDetailsDto;
import com.cohkomapa.imageboard.media.entity.MediaEntity;
import com.cohkomapa.imageboard.media.enums.MediaStatus;
import com.cohkomapa.imageboard.media.enums.SupportedMediaType;
import com.cohkomapa.imageboard.media.exception.MediaProcessingException;
import com.cohkomapa.imageboard.media.exception.ResourceNotFoundException;
import com.cohkomapa.imageboard.media.mapper.MediaMapper;
import com.cohkomapa.imageboard.media.repository.MediaRepository;
import com.cohkomapa.imageboard.media.service.objectstorage.ObjectStorageService;
import com.cohkomapa.imageboard.media.service.validation.MediaFileValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import static com.cohkomapa.imageboard.media.enums.MediaStatus.MARKED_TO_DELETE;

@Service
public class MediaService {

    private final MediaService self;
    private final MediaFileValidator mediaFileValidator;
    private final ObjectStorageService objectStorageService;
    private final MediaObjectKeyGenerator mediaObjectKeyGenerator;

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    public MediaService(
            @Lazy MediaService self,
            MediaFileValidator mediaFileValidator,
            ObjectStorageService objectStorageService,
            MediaObjectKeyGenerator mediaObjectKeyGenerator,
            MediaRepository mediaRepository,
            MediaMapper mediaMapper
    ) {
        this.self = self;
        this.mediaFileValidator = mediaFileValidator;
        this.objectStorageService = objectStorageService;
        this.mediaObjectKeyGenerator = mediaObjectKeyGenerator;
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Transactional(readOnly = true)
    public MediaEntity getEntityById(UUID mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media", mediaId));
    }

    public MediaDetailsDto upload(MultipartFile file) {
        SupportedMediaType mediaType = mediaFileValidator.validate(file);
        UUID mediaId = UUID.randomUUID();
        String objectKey = mediaObjectKeyGenerator.generateOrdinalKey(mediaId, mediaType.getExtension());

        try (InputStream inputStream = file.getInputStream()) {
            self.createPending(mediaId, objectKey, file, mediaType);
            objectStorageService.upload(objectKey, inputStream, file.getSize(), mediaType.getContentType());
            return self.markReady(mediaId);

        } catch (IOException ex) {
            throw new MediaProcessingException("Failed to read uploaded file", ex);
        }
    }

    @Transactional
    public void createPending(
            UUID mediaId,
            String objectKey,
            MultipartFile file,
            SupportedMediaType mediaType
    ) {
        MediaEntity media = new MediaEntity();
        media.setId(mediaId);
        media.setObjectKey(objectKey);
        media.setOriginalFileName(file.getOriginalFilename());
        media.setContentType(mediaType.getContentType());
        media.setSize(file.getSize());
        media.setStatus(MediaStatus.PENDING);
        mediaRepository.save(media);
    }

    @Transactional
    public MediaDetailsDto markReady(UUID mediaId) {
        MediaEntity media = getEntityById(mediaId);
        media.setStatus(MediaStatus.READY);
        return mediaMapper.mapToDetailsDto(media);
    }

    @Transactional
    public void markToDelete(UUID mediaId) {
        MediaEntity media = self.getEntityById(mediaId);
        media.setStatus(MARKED_TO_DELETE);
    }

    @Transactional
    public void deleteAllByIds(Collection<UUID> mediaIds) {
        mediaRepository.deleteAllByIdInBatch(mediaIds);
    }
}