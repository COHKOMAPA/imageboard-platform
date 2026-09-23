package com.cohkomapa.imageboard.board.client;

import com.cohkomapa.imageboard.board.dto.media.MediaValidationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

@FeignClient(
        name = "media-service",
        url = "${clients.media-service.url}"
)
public interface MediaClient {

    @GetMapping("/internal/media/validate")
    MediaValidationResponseDto validate(
            @RequestParam("mediaIds") Set<UUID> mediaIds
    );
}