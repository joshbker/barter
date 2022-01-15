package dev.devous.barter.service;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NameHistoryServiceResult extends ServiceResult {
    private final @NotNull List<String> previousNames = new ArrayList<>();

    /**
     * Instantiates a wrapper for a name history json response.<br><br>
     *
     * @param response the json response, a {@link JsonElement}.
     */
    NameHistoryServiceResult(final @NotNull JsonElement response) {
        super(response);
        for (JsonElement element : response.getAsJsonArray()) {
            previousNames.add(element.getAsJsonObject().get("name").getAsString());
        }
        Collections.reverse(previousNames);
    }

    /**
     * Obtains a list of previous names from the profile, in ascending order (most recent first).<br><br>
     *
     * @return A {@link List} of the profile's previous names.
     */
    public @NotNull List<String> previousNames() {
        return Collections.unmodifiableList(previousNames);
    }
}
