package com.cohkomapa.imageboard.board.dto.post;

import io.swagger.v3.oas.annotations.Parameter;

import java.time.ZonedDateTime;

public record PostFilter(

        @Parameter(description = "Заголовок")
        String title,

        @Parameter(description = "Дата создания от (включительно)")
        ZonedDateTime createdFrom,

        @Parameter(description = "Дата создания по (включительно)")
        ZonedDateTime createdTo
) {
}