package com.amazonaws.services.dynamodbv2;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.function.Consumer;

public final class Helpers {
    private Helpers() {}

    /**
     * Set up static calls using Mockito.mockStatic
     */
    public static void setOwnerNameToUuid(UUIDBlock block) {
        final UUID uuid = UUID.randomUUID(); //get UUID for use in test

        try(MockedStatic<UUID> mockUUID = Mockito.mockStatic(UUID.class);
            MockedStatic<InetAddress> mockInet = Mockito.mockStatic(InetAddress.class)) {
            mockUUID.when(UUID::randomUUID).thenReturn(uuid);
            mockInet.when(InetAddress::getLocalHost).thenThrow(new UnknownHostException());
            block.accept(uuid);
        } catch (Exception e) {
            sneakyThrow(e);
        }
    }

    @FunctionalInterface
    public interface UUIDBlock {
        void accept(UUID value) throws Exception;
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }}
