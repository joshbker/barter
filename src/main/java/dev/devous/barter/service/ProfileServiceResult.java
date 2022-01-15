package dev.devous.barter.service;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public final class ProfileServiceResult extends ServiceResult {
    ProfileServiceResult(final @NotNull JsonElement response) {
        super(response);
    }

    public @NotNull String name() {
        return responseObject().get("name").getAsString();
    }

    public @NotNull String texture() {
        return responseObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
    }

    public @NotNull String signature() {
        return responseObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("signature").getAsString();
    }
}
