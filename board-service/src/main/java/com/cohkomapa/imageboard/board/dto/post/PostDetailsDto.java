package com.cohkomapa.imageboard.board.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(description = "DTO для просмотра подробной информации о посте")
public record PostDetailsDto(

        @Schema(description = "ID")
        UUID id,

        @Schema(description = "Заголовок")
        String title,

        @Schema(description = "Описание")
        String description,

        @Schema(description = "Дата и время создания")
        ZonedDateTime createdAt,

        @Schema(description = "Дата и время последнего обновления")
        ZonedDateTime updatedAt
) {
}