package dev.devous.barter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public final class Barter {
    private final @NotNull ExecutorService executorService;
    private final @NotNull MojangService mojangService = new MojangService();

    public Barter(final @NotNull ExecutorService executorService) {
        this.executorService = executorService;
    }

    public @NotNull UUID nameToUUID(final @NotNull String name) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.uuidQuery(name).uuid()).get();
    }

    public @NotNull String uuidToName(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.profileQuery(uuid).name()).get();
    }

    public @NotNull String base64Texture(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.profileQuery(uuid).texture()).get();
    }

    public @NotNull String base64Signature(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.profileQuery(uuid).texture()).get();
    }

    public @NotNull List<String> nameHistory(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.nameHistoryQuery(uuid).previousNames()).get();
    }
}
