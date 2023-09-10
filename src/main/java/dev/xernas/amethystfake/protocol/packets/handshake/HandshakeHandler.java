package dev.xernas.amethystfake.protocol.packets.handshake;

import dev.xernas.amethystfake.protocol.packets.IHandler;
import dev.xernas.amethystfake.protocol.packets.status.StatusHandler;
import dev.xernas.amethystfake.protocol.packets.utils.NetworkManager;

import java.io.IOException;
import java.util.Map;

public class HandshakeHandler implements IHandler {

    private final NetworkManager manager;

    public HandshakeHandler(NetworkManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle() throws IOException {
        Map<String, Object> response = manager.waitForPacket(new HandshakePacket());
        if (response != null) {
            if ((Integer) response.get("state") == 1) {
                manager.nextState(new StatusHandler(manager));
            } else if ((Integer) response.get("state") == 2) {
                manager.getLogger().warn(manager.getClient().getRemoteSocketAddress().toString() + " tried to log in");
                manager.disconnect();
                manager.getClient().close();
            }
            else {
                manager.getLogger().warn("Wrong state on Handshake");
                manager.getClient().close();
                manager.getLogger().disconnect(manager.getClient());
            }
        }
    }
}
