package dev.xernas.glowstone.protocol.packets.status;

import dev.xernas.glowstone.protocol.packets.IHandler;
import dev.xernas.glowstone.protocol.packets.utils.NetworkManager;

import java.io.IOException;
import java.util.Map;

public class StatusHandler implements IHandler {

    private final NetworkManager manager;

    public StatusHandler(NetworkManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle() throws IOException {
        Map<String, Object> statusPacket = manager.waitForPacket(new StatusPacket());
        if (statusPacket != null) {
            manager.sendPacket(new StatusPacket(manager.getInfos()));
            Map<String, Object> pingPacket = manager.waitForPacket(new PingPongPacket());
            if (pingPacket != null) {
                manager.sendPacket(new PingPongPacket((Long) pingPacket.get("time")));
            }
        }

    }
}
