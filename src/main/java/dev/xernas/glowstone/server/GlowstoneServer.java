package dev.xernas.glowstone.server;

import dev.xernas.glowstone.protocol.models.ServerInfos;
import dev.xernas.glowstone.protocol.models.chat.Component;
import dev.xernas.glowstone.protocol.packets.handshake.HandshakeHandler;
import dev.xernas.glowstone.protocol.packets.utils.MCByteBuf;
import dev.xernas.glowstone.protocol.packets.utils.NetworkManager;
import dev.xernas.glowstone.server.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GlowstoneServer extends Thread {

    private final Socket client;
    private final Logger logger;
    private final ServerInfos infos;
    private final Component kickReason;

    public GlowstoneServer(Socket client, Logger logger, ServerInfos infos, Component kickReason) {
        this.client = client;
        this.logger = logger;
        this.infos = infos;
        this.kickReason = kickReason;
    }

    @Override
    public void run() {
        try {
            MCByteBuf byteBuf = new MCByteBuf(new DataOutputStream(client.getOutputStream()), new DataInputStream(client.getInputStream()));
            NetworkManager manager = new NetworkManager(byteBuf, client, logger, infos, kickReason);
            manager.nextState(new HandshakeHandler(manager));
        } catch (IOException e) {
            try {
                client.close();
                logger.warn(e.getMessage());
                logger.disconnect(client);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
