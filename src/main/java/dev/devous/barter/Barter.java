package dev.devous.barter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public final class Barter {
    private final @NotNull ExecutorService executorService;
    private final @NotNull MojangService mojangService = new MojangService();

    /**
     * Instantiates the main point of contact for the library.<br><br>
     *
     * @param executorService an executor for the requests to be made on, an {@link ExecutorService}.
     */
    public Barter(final @NotNull ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Get a specified player's unique id from their name.<br><br>
     *
     * @param name the name of a player.
     * @return The player's unique id, a {@link UUID}.
     * @throws ExecutionException if the executor encounters an exception.
     * @throws InterruptedException if the executor is interrupted.
     */
    public @NotNull UUID nameToUUID(final @NotNull String name) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.uuidQuery(name).uuid()).get();
    }

    /**
     * Get a specified player's name from their unique id.<br><br>
     *
     * @param uuid the unique id of a player.
     * @return The player's name, a {@link String}.
     * @throws ExecutionException if the executor encounters an exception.
     * @throws InterruptedException if the executor is interrupted.
     */
    public @NotNull String uuidToName(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.profileQuery(uuid).name()).get();
    }

    /**
     * Get a specified player's texture value from their unique id.<br><br>
     *
     * @param uuid the unique id of a player.
     * @return The player's texture value, a base64 {@link String}.
     * @throws ExecutionException if the executor encounters an exception.
     * @throws InterruptedException if the executor is interrupted.
     */
    public @NotNull String base64Texture(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.profileQuery(uuid).texture()).get();
    }

    /**
     * Get a specified player's texture signature from their unique id.<br><br>
     *
     * @param uuid the unique id of a player.
     * @return The player's texture signature, a base64 {@link String}.
     * @throws ExecutionException if the executor encounters an exception.
     * @throws InterruptedException if the executor is interrupted.
     */
    public @NotNull String base64Signature(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.profileQuery(uuid).signature()).get();
    }

    /**
     * Get a specified player's name history from their unique id.<br><br>
     *
     * @param uuid the unique id of a player.
     * @return A {@link List} of the player's previous names in ascending order (most recent first).
     * @throws ExecutionException if the executor encounters an exception.
     * @throws InterruptedException if the executor is interrupted.
     */
    public @NotNull List<String> nameHistory(final @NotNull UUID uuid) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> mojangService.nameHistoryQuery(uuid).previousNames()).get();
    }
}
