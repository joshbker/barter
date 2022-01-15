package dev.devous.barter.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ServiceResult {
    private final @NotNull JsonElement response;

    /**
     * Instantiates a wrapper for a json response.<br><br>
     *
     * @param response the json response, a {@link JsonElement}.
     */
    ServiceResult(final @NotNull JsonElement response) {
        this.response = response;
    }

    /**
     * Get the json response.<br><br>
     *
     * @return The response, a {@link JsonElement}.
     */
    public @NotNull JsonElement response() {
        return response;
    }

    /**
     * Get the json response as an object.<br><br>
     *
     * @return The response as a {@link JsonObject}.
     */
    public @NotNull JsonObject responseObject() {
        return response.getAsJsonObject();
    }

    /**
     * Get the profile's unique id.<br><br>
     *
     * @return The profile's id, a {@link UUID}.
     */
    public @NotNull UUID uuid() {
        String withoutDashes = responseObject().get("id").getAsString();
        return new UUID(Long.parseUnsignedLong(withoutDashes.substring(0, 16), 16),
                Long.parseUnsignedLong(withoutDashes.substring(16, 32), 16));
    }
}
