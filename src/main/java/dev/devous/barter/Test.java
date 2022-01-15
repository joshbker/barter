package dev.devous.barter;

import dev.devous.barter.exception.EndpointConnectionException;

import java.io.IOException;
import java.util.UUID;

public final class Test {
    public static void main(String[] args) throws IOException, EndpointConnectionException {
        Barter barter = Barter.instance();
        UUID uuid = barter.usernameToUUID("Devous");
        System.out.println(uuid);
    }
}
