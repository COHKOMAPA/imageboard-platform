package com.cohkomapa.imageboard.media.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(description = "DTO для просмотра подробной информации о медиафайле")
public record MediaDetailsDto(

        @Schema(description = "ID")
        UUID id,

        @Schema(description = "Оригинальное имя")
        String originalFileName,

        @Schema(description = "MIME-тип")
        String contentType,

        @Schema(description = "Размер в байтах")
        long size,

        @Schema(description = "Дата и время создания")
        ZonedDateTime createdAt
) {
}