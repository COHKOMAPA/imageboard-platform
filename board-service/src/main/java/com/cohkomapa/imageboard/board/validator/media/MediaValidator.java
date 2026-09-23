package com.cohkomapa.imageboard.board.validator.media;

import com.cohkomapa.imageboard.board.client.MediaClient;
import com.cohkomapa.imageboard.board.exception.InvalidMediaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaValidator {

    private final MediaClient mediaClient;

    public void validateMedia(Set<UUID> mediaRequestIds) {
        Set<UUID> validMediaIds = mediaClient.validate(mediaRequestIds).validMediaIds();

        Set<UUID> invalidMediaIds = new HashSet<>(mediaRequestIds);
        invalidMediaIds.removeAll(validMediaIds);

        if (!invalidMediaIds.isEmpty()) {
            throw new InvalidMediaException(invalidMediaIds);
        }
    }
}