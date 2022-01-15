package dev.devous.barter.service;

import com.google.gson.JsonObject;
import dev.devous.barter.exception.EndpointConnectionException;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public interface BarterService {
    ServiceResult uuidQuery(final @NotNull String username) throws EndpointConnectionException, IOException;

    default ServiceResult serviceResult(final @NotNull JsonObject jsonObject) {
        return new ServiceResult(jsonObject);
    }

    default Optional<HttpsURLConnection> establishConnection(final @NotNull String endpoint) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(endpoint).openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            connection.setDoOutput(true);

            connection.connect();

            return Optional.of(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
