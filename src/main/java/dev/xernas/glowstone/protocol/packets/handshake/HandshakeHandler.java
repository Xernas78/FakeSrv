package dev.xernas.glowstone.protocol.packets.handshake;

import dev.xernas.glowstone.protocol.packets.IHandler;
import dev.xernas.glowstone.protocol.packets.status.StatusHandler;
import dev.xernas.glowstone.protocol.packets.utils.PacketManager;

import java.io.IOException;
import java.util.Map;

public class HandshakeHandler implements IHandler {

    private final PacketManager manager;

    public HandshakeHandler(PacketManager manager) {
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
                manager.getClient().close();
                manager.getLogger().disconnect(manager.getClient());
            }
            else {
                manager.getLogger().warn("Wrong state on Handshake");
                manager.getClient().close();
                manager.getLogger().disconnect(manager.getClient());
            }
        }
    }
}
