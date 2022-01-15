package dev.devous.barter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.devous.barter.exception.EndpointConnectionException;
import dev.devous.barter.service.BarterService;
import dev.devous.barter.service.ServiceResult;
import dev.devous.barter.service.ServiceResultType;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public final class MojangService implements BarterService {
    MojangService() {

    }

    @Override
    public ServiceResult uuidQuery(final @NotNull String name) throws EndpointConnectionException, IOException {
        HttpsURLConnection connection =
                establishConnection("https://api.mojang.com/users/profiles/minecraft/" + name);

        JsonObject jsonObject = JsonParser.parseReader(new BufferedReader(
                new InputStreamReader(connection.getInputStream()))).getAsJsonObject();
        connection.disconnect();
        return serviceResult(jsonObject, ServiceResultType.DEFAULT);
    }

    @Override
    public ServiceResult profileQuery(@NotNull UUID uid) throws EndpointConnectionException, IOException {
        HttpsURLConnection connection =
                establishConnection("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);

        JsonObject jsonObject = JsonParser.parseReader(new BufferedReader(
                new InputStreamReader(connection.getInputStream()))).getAsJsonObject();
        connection.disconnect();
        return serviceResult(jsonObject, ServiceResultType.PROFILE);
    }
}
