package com.cohkomapa.imageboard.board.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO для просмотра краткой информации о посте")
public record PostShortDto(

        @Schema(description = "ID")
        UUID id,

        @Schema(description = "Заголовок")
        String title
) {
}