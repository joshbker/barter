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
        return (ProfileServiceResult) serviceResult(jsonObject, ServiceResultType.PROFILE);
    }

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
        return (NameHistoryServiceResult) serviceResult(jsonArray, ServiceResultType.NAME_HISTORY);
    }
}
