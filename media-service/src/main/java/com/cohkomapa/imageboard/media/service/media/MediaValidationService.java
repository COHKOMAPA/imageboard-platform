package com.cohkomapa.imageboard.media.service.media;

import com.cohkomapa.imageboard.media.dto.response.MediaValidationResponseDto;
import com.cohkomapa.imageboard.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static com.cohkomapa.imageboard.media.enums.MediaStatus.READY;

@Service
@RequiredArgsConstructor
public class MediaValidationService {

    private final MediaRepository mediaRepository;

    @Transactional(readOnly = true)
    public MediaValidationResponseDto validate(Collection<UUID> mediaIds) {
        Set<UUID> readyMediaIds = mediaRepository.findByIdsAndStatus(mediaIds, READY);
        return new MediaValidationResponseDto(readyMediaIds);
    }
}