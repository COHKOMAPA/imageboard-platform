package com.cohkomapa.imageboard.media.service.media;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MediaObjectKeyGenerator {

    public String generateOrdinalKey(UUID mediaId, String extension) {
        return mediaId + "/ordinal." + extension;
    }
}