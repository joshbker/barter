package dev.devous.barter;

import dev.devous.barter.exception.EndpointConnectionException;
import dev.devous.barter.service.ProfileServiceResult;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public final class Barter {
    private final @NotNull ExecutorService executorService;
    private final @NotNull MojangService mojangService = new MojangService();

    public Barter(final @NotNull ExecutorService executorService) {
        this.executorService = executorService;
    }

    public @NotNull UUID nameToUUID(final @NotNull String name) throws EndpointConnectionException, ExecutionException,
            InterruptedException {
        return executorService.submit(() -> mojangService.uuidQuery(name).uuid()).get();
    }

    public @NotNull String uuidToName(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> ((ProfileServiceResult) mojangService.profileQuery(uuid)).name()).get();
    }

    public @NotNull String base64Texture(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> ((ProfileServiceResult) mojangService.profileQuery(uuid)).texture()).get();
    }
}
