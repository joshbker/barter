package dev.devous.barter.exception;

import org.jetbrains.annotations.NotNull;

public final class EndpointConnectionException extends Exception {
    public EndpointConnectionException(final @NotNull String message) {
        super(message);
    }
}
