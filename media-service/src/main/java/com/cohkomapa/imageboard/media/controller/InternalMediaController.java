package com.cohkomapa.imageboard.media.controller;

import com.cohkomapa.imageboard.media.dto.response.MediaValidationResponseDto;
import com.cohkomapa.imageboard.media.service.media.MediaValidationService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/internal/media")
@RequiredArgsConstructor
public class InternalMediaController {

    private final MediaValidationService mediaValidationService;

    @GetMapping("/validate")
    public MediaValidationResponseDto validate(
            @RequestParam
            @Size(min = 1, max = 20)
            Set<UUID> mediaIds
    ) {
        return mediaValidationService.validate(mediaIds);
    }
}