package com.cohkomapa.imageboard.board.dto.media;

import java.util.Set;
import java.util.UUID;

public record MediaValidationResponseDto(
        Set<UUID> validMediaIds
) {
}