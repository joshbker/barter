package dev.devous.barter.exception;

import org.jetbrains.annotations.NotNull;

public final class EndpointConnectionException extends RuntimeException {
    public EndpointConnectionException(final @NotNull String message) {
        super(message);
    }

    public EndpointConnectionException(final @NotNull Throwable cause) {
        super(cause);
    }

    public EndpointConnectionException(final @NotNull String message, final @NotNull Throwable cause) {
        super(message, cause);
    }
}
