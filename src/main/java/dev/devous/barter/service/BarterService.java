package dev.devous.barter.service;

import com.google.gson.JsonElement;
import dev.devous.barter.exception.EndpointConnectionException;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public interface BarterService {
    ServiceResult uuidQuery(final @NotNull String name) throws EndpointConnectionException, IOException;

    ProfileServiceResult profileQuery(final @NotNull UUID uid) throws EndpointConnectionException, IOException;

    NameHistoryServiceResult nameHistoryQuery(final @NotNull UUID uid) throws EndpointConnectionException, IOException;

    default @NotNull ServiceResult serviceResult(final @NotNull JsonElement jsonElement,
                                                 final @NotNull ServiceResultType resultType) {
        switch (resultType) {
            case PROFILE -> {
                return new ProfileServiceResult(jsonElement);
            }
            case NAME_HISTORY -> {
                return new NameHistoryServiceResult(jsonElement);
            }
            default -> {
                return new ServiceResult(jsonElement);
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
        } catch (MalformedURLException e) {
            throw new EndpointConnectionException("Unable to establish connection: Malformed URL.", e);
        } catch (IOException e) {
            throw new EndpointConnectionException("Unable to establish connection:", e);
        }
    }
}
