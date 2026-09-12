package com.cohkomapa.imageboard.media.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum SupportedMediaType {

    JPEG("image/jpeg", "jpg"),
    PNG("image/png", "png"),
    WEBP("image/webp", "webp");

    SupportedMediaType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    private final String contentType;
    private final String extension;

    public static Optional<SupportedMediaType> fromContentType(String contentType) {
        return Arrays.stream(values())
                .filter(type -> type.contentType.equals(contentType))
                .findFirst();
    }
}