package dev.devous.barter.service;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ServiceResult {
    private final @NotNull JsonObject response;

    ServiceResult(final @NotNull JsonObject response) {
        this.response = response;
    }

    public @NotNull JsonObject response() {
        return response;
    }

    public @NotNull UUID uuid() {
        String withoutDashes = response.get("id").getAsString();
        return new UUID(Long.parseUnsignedLong(withoutDashes.substring(0, 16), 16),
                Long.parseUnsignedLong(withoutDashes.substring(16, 32), 16));
    }
}
