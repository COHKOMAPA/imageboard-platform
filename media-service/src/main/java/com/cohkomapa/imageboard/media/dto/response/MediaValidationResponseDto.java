package com.cohkomapa.imageboard.media.dto.response;

import java.util.Set;
import java.util.UUID;

public record MediaValidationResponseDto(
        Set<UUID> validMediaIds
) {
}