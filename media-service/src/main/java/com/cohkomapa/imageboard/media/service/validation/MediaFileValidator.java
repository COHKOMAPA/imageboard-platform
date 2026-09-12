package com.cohkomapa.imageboard.media.service.validation;

import com.cohkomapa.imageboard.media.enums.SupportedMediaType;
import com.cohkomapa.imageboard.media.exception.InvalidMediaException;
import com.cohkomapa.imageboard.media.exception.MediaProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class MediaFileValidator {

    private final DataSize maxFileSize;
    private final Tika tika = new Tika();

    public MediaFileValidator(
            @Value("${media.max-file-size}") DataSize maxFileSize
    ) {
        this.maxFileSize = maxFileSize;
    }

    public SupportedMediaType validate(MultipartFile file) {

        if (file.isEmpty()) {
            throw new InvalidMediaException("File must not be empty");
        }
        String originalFileName = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFileName)) {
            throw new InvalidMediaException("File name must not be empty");
        }
        if (originalFileName.length() > 255) {
            throw new InvalidMediaException("File name is too long");
        }
        if (file.getSize() > maxFileSize.toBytes()) {
            throw new InvalidMediaException("File is too large");
        }
        String contentType = detectContentType(file);
        return SupportedMediaType.fromContentType(contentType)
                .orElseThrow(() -> new InvalidMediaException("Unsupported file type"));
    }

    String detectContentType(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return tika.detect(inputStream);
        } catch (IOException ex) {
            throw new MediaProcessingException("Failed to detect media type", ex);
        }
    }
}