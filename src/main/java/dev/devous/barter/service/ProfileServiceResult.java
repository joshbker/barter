package dev.devous.barter.service;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public final class ProfileServiceResult extends ServiceResult {
    /**
     * Instantiates a wrapper for a profile json response.<br><br>
     *
     * @param response the json response, a {@link JsonElement}.
     */
    ProfileServiceResult(final @NotNull JsonElement response) {
        super(response);
    }

    /**
     * Get the profile's current name from the json response.<br><br>
     *
     * @return The profile's name - {@link String}.
     */
    public @NotNull String name() {
        return responseObject().get("name").getAsString();
    }

    /**
     * Get the profile's current texture value.<br><br>
     *
     * @return The profile's texture value (base64) - {@link String}.
     */
    public @NotNull String texture() {
        return responseObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
    }

    /**
     * Get the profile's current texture signature.<br><br>
     *
     * @return The profile's texture signature (base64) - {@link String}.
     */
    public @NotNull String signature() {
        return responseObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("signature").getAsString();
    }
}
