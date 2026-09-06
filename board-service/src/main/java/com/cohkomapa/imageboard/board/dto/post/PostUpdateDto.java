package com.cohkomapa.imageboard.board.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для обновления поста")
public record PostUpdateDto(

        @Schema(description = "Заголовок", example = "Текст заголовка")
        @NotBlank
        @Size(max = 200)
        String title,

        @Schema(description = "Описание", example = "Текст описания")
        @NotBlank
        @Size(max = 500)
        String description
) {
}