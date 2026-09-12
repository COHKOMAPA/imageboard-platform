package com.cohkomapa.imageboard.media.service.objectstorage;

import com.cohkomapa.imageboard.media.config.minio.MinioProperties;
import com.cohkomapa.imageboard.media.exception.ObjectStorageException;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioObjectStorageService implements ObjectStorageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public void upload(
            String objectKey,
            InputStream inputStream,
            long size,
            String contentType
    ) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(objectKey)
                            .stream(inputStream, size, -1L)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception ex) {
            throw new ObjectStorageException("Failed to upload object", ex);
        }
    }

    @Override
    public String generatePresignedUrl(String objectKey, Duration duration) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Http.Method.GET)
                            .bucket(minioProperties.bucketName())
                            .object(objectKey)
                            .expiry(Math.toIntExact(duration.toSeconds()), TimeUnit.SECONDS)
                            .build()
            );
        } catch (Exception ex) {
            throw new ObjectStorageException("Failed to generate presigned URL for object " + objectKey, ex);
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.bucketName())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception ex) {
            throw new ObjectStorageException(
                    "Failed to delete object " + objectKey, ex);
        }
    }
}