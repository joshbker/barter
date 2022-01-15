package dev.devous.barter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.devous.barter.exception.EndpointConnectionException;
import dev.devous.barter.service.BarterService;
import dev.devous.barter.service.ServiceResult;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public final class MojangService implements BarterService {
    MojangService() {

    }

    @Override
    public ServiceResult uuidQuery(final @NotNull String username) throws EndpointConnectionException, IOException {
        Optional<HttpsURLConnection> connection =
                establishConnection("https://api.mojang.com/users/profiles/minecraft/" + username);

        if (connection.isEmpty()) {
            throw new EndpointConnectionException("Unable to establish a connection.");
        }

        JsonObject jsonObject = JsonParser.parseReader(new BufferedReader(
                new InputStreamReader(connection.get().getInputStream()))).getAsJsonObject();
        connection.get().disconnect();
        return serviceResult(jsonObject);
    }
}
