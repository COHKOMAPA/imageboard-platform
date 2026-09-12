package com.cohkomapa.imageboard.media.service.objectstorage;

import java.io.InputStream;
import java.time.Duration;

public interface ObjectStorageService {

    void upload(
            String objectKey,
            InputStream inputStream,
            long size,
            String contentType
    );

    String generatePresignedUrl(String objectKey, Duration duration);

    void delete(String objectKey);

    void deleteByPrefix(String prefix);
}