package dev.devous.barter.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ServiceResult {
    private final @NotNull JsonElement response;

    ServiceResult(final @NotNull JsonElement response) {
        this.response = response;
    }

    public @NotNull JsonElement response() {
        return response;
    }

    public @NotNull JsonObject responseObject() {
        return response.getAsJsonObject();
    }

    public @NotNull UUID uuid() {
        String withoutDashes = responseObject().get("id").getAsString();
        return new UUID(Long.parseUnsignedLong(withoutDashes.substring(0, 16), 16),
                Long.parseUnsignedLong(withoutDashes.substring(16, 32), 16));
    }
}
