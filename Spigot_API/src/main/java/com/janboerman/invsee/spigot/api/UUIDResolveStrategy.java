package com.janboerman.invsee.spigot.api;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UUIDResolveStrategy {

    public CompletableFuture<Optional<UUID>> resolveUUID(String userName);

}
