package dev.devous.barter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import dev.devous.barter.exception.EndpointConnectionException;
import dev.devous.barter.service.*;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

final class MojangService implements BarterService {
    MojangService() {

    }

    /**
     * Get a wrapped json response {@link ServiceResult} of a player's id & name, given their name.<br><br>
     *
     * @param name the name of a player.
     * @return {@link ServiceResult} – a wrapper for the json response containing id & name.
     * @throws EndpointConnectionException unable to contact endpoint.
     * @throws IOException                 unable to read input stream.
     */
    @Override
    public ServiceResult uuidQuery(final @NotNull String name) throws EndpointConnectionException, IOException {
        HttpsURLConnection connection =
                establishConnection("https://api.mojang.com/users/profiles/minecraft/" + name);

        JsonObject jsonObject;
        try (JsonReader jsonReader =
                     new JsonReader(new BufferedReader(new InputStreamReader(connection.getInputStream())))) {
            jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        }
        return serviceResult(jsonObject, ServiceResultType.DEFAULT);
    }

    /**
     * Get a wrapped json response {@link ServiceResult} of a player's profile, given their unique id.<br><br>
     *
     * @param uid the unique id of a player.
     * @return {@link ProfileServiceResult} – a wrapper for the json response containing a profile.
     * @throws EndpointConnectionException unable to contact endpoint.
     * @throws IOException                 unable to read input stream.
     */
    @Override
    public ProfileServiceResult profileQuery(final @NotNull UUID uid) throws EndpointConnectionException, IOException {
        HttpsURLConnection connection =
                establishConnection("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);

        JsonObject jsonObject;
        try (JsonReader jsonReader =
                     new JsonReader(new BufferedReader(new InputStreamReader(connection.getInputStream())))) {
            jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        }
        connection.disconnect();
        return serviceResult(jsonObject, ServiceResultType.PROFILE);
    }

    /**
     * Get a wrapped json response {@link ServiceResult} of a player's name history, given their unique id.<br><br>
     *
     * @param uid the unique id of a player.
     * @return {@link NameHistoryServiceResult} – a wrapper for the json response containing name history.
     * @throws EndpointConnectionException unable to contact endpoint.
     * @throws IOException                 unable to read input stream.
     */
    @Override
    public NameHistoryServiceResult nameHistoryQuery(final @NotNull UUID uid) throws EndpointConnectionException,
            IOException {
        HttpsURLConnection connection =
                establishConnection("https://api.mojang.com/user/profiles/" + uid + "/names");

        JsonArray jsonArray;
        try (JsonReader jsonReader =
                     new JsonReader(new BufferedReader(new InputStreamReader(connection.getInputStream())))) {
            jsonArray = JsonParser.parseReader(jsonReader).getAsJsonArray();
        }
        connection.disconnect();
        return serviceResult(jsonArray, ServiceResultType.NAME_HISTORY);
    }
}
