package com.cohkomapa.imageboard.board.exception;

import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
public class InvalidMediaException extends RuntimeException {

    private final Set<UUID> invalidMediaIds;

    public InvalidMediaException(Set<UUID> invalidMediaIds) {
        super("Some media are not available");
        this.invalidMediaIds = invalidMediaIds;
    }
}
