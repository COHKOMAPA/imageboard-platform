package com.cohkomapa.imageboard.board.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания поста")
public record PostCreateDto(

        @Schema(description = "Заголовок", example = "Текст заголовка")
        @Size(max = 200)
        String title,

        @Schema(description = "Описание", example = "Текст описания")
        @Size(max = 500)
        String description
) {
}