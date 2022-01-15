package dev.devous.barter.service;

import com.google.gson.JsonObject;
import dev.devous.barter.exception.EndpointConnectionException;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public interface BarterService {
    ServiceResult uuidQuery(final @NotNull String name) throws EndpointConnectionException, IOException;

    ServiceResult profileQuery(final @NotNull UUID uid) throws EndpointConnectionException, IOException;

    default @NotNull ServiceResult serviceResult(final @NotNull JsonObject jsonObject,
                                                 final @NotNull ServiceResultType resultType) {
        switch (resultType) {
            case PROFILE -> {
                return new ProfileServiceResult(jsonObject);
            }
            default -> {
                return new ServiceResult(jsonObject);
            }
        }
    }

    default @NotNull HttpsURLConnection establishConnection(final @NotNull String endpoint) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(endpoint).openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            connection.setDoOutput(true);

            connection.connect();

            return connection;
        } catch (IOException e) {
            throw new EndpointConnectionException("Unable to establish connection.", e);
        }
    }
}
