package com.cohkomapa.imageboard.board.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

@Schema(description = "DTO для создания поста")
public record PostCreateDto(

        @Schema(description = "Заголовок", example = "Текст заголовка")
        @Size(max = 200)
        String title,

        @Schema(description = "Описание", example = "Текст описания")
        @Size(max = 500)
        String description,

        @Schema(description = "ID прикрепленных медиафайлов")
        @NotEmpty
        @Size(max = 20)
        @UniqueElements
        List<@NotNull UUID> mediaIds
) {
}