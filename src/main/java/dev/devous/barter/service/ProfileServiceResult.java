package dev.devous.barter.service;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public final class ProfileServiceResult extends ServiceResult {
    ProfileServiceResult(@NotNull JsonObject response) {
        super(response);
    }

    public @NotNull String name() {
        return response().get("name").getAsString();
    }

    public @NotNull String texture() {
        return response().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
    }
}
