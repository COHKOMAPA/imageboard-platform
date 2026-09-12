package com.cohkomapa.imageboard.media.controller;

import com.cohkomapa.imageboard.media.dto.MediaDetailsDto;
import com.cohkomapa.imageboard.media.service.media.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MediaDetailsDto upload(
            @RequestPart("file") MultipartFile file
    ) {
        return mediaService.upload(file);
    }
}