package dev.devous.barter.service;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NameHistoryServiceResult extends ServiceResult {
    private final @NotNull List<String> previousNames = new ArrayList<>();

    NameHistoryServiceResult(final @NotNull JsonElement response) {
        super(response);
        for (JsonElement element : response.getAsJsonArray()) {
            previousNames.add(element.getAsJsonObject().get("name").getAsString());
        }
        Collections.reverse(previousNames);
    }

    public @NotNull List<String> previousNames() {
        return Collections.unmodifiableList(previousNames);
    }
}
