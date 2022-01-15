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
    /**
     * Defines a method to obtain the uuid of a player given their name.<br><br>
     *
     * @param name the name of a player.
     * @return {@link ServiceResult} – a wrapper for the json response.
     * @throws EndpointConnectionException unable to contact endpoint.
     * @throws IOException                 unable to read input stream.
     */
    ServiceResult uuidQuery(final @NotNull String name) throws EndpointConnectionException, IOException;

    /**
     * Defines a method to obtain the profile of a player given their uuid.<br><br>
     *
     * @param uid the unique id of a player.
     * @return {@link ProfileServiceResult} – a wrapper for the profile json response.
     * @throws EndpointConnectionException unable to contact endpoint.
     * @throws IOException                 unable to read input stream.
     */
    ProfileServiceResult profileQuery(final @NotNull UUID uid) throws EndpointConnectionException, IOException;

    /**
     * Defines a method to obtain the name history of a player given their uuid.<br><br>
     *
     * @param uid the unique id of a player.
     * @return {@link NameHistoryServiceResult} – a wrapper for the name history json response.
     * @throws EndpointConnectionException unable to contact endpoint.
     * @throws IOException                 unable to read input stream.
     */
    NameHistoryServiceResult nameHistoryQuery(final @NotNull UUID uid) throws EndpointConnectionException, IOException;

    /**
     * Defines a method to obtain an implementation of {@link ServiceResult}.<br><br>
     *
     * @param jsonElement the unique id of a player.
     * @param resultType  A {@link ServiceResultType}, the type of wrapper used for the json response.
     * @return A wrapper for the json response.
     */
    default @NotNull <T> T serviceResult(final @NotNull JsonElement jsonElement,
                                         final @NotNull ServiceResultType resultType) {
        switch (resultType) {
            case PROFILE -> {
                return (T) new ProfileServiceResult(jsonElement);
            }
            case NAME_HISTORY -> {
                return (T) new NameHistoryServiceResult(jsonElement);
            }
            default -> {
                return (T) new ServiceResult(jsonElement);
            }
        }
    }

    /**
     * Establishes a connection with {@link HttpsURLConnection}.<br><br>
     *
     * @param endpoint the url endpoint to connect to.
     * @return {@link HttpsURLConnection} - a connection to the endpoint.
     * @throws EndpointConnectionException unable to contact endpoint.
     */
    default @NotNull HttpsURLConnection establishConnection(final @NotNull String endpoint)
            throws EndpointConnectionException {
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
