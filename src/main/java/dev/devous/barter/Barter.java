package dev.devous.barter;

import dev.devous.barter.exception.EndpointConnectionException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public final class Barter {
    private static final @NotNull Barter instance = new Barter();

    public static @NotNull Barter instance() {
        return instance;
    }

    private final @NotNull MojangService mojangService = new MojangService();

    public @NotNull UUID usernameToUUID(final @NotNull String username) throws IOException,
            EndpointConnectionException {
        return mojangService.uuidQuery(username).uuid();
    }
}
