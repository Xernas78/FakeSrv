package dev.xernas.fakesrv.server;

import dev.xernas.fakesrv.protocol.models.ServerInfos;
import dev.xernas.fakesrv.protocol.models.chat.Component;
import dev.xernas.fakesrv.protocol.packets.handshake.HandshakeHandler;
import dev.xernas.fakesrv.protocol.packets.utils.MCByteBuf;
import dev.xernas.fakesrv.protocol.packets.utils.NetworkManager;
import dev.xernas.fakesrv.server.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FakeServer extends Thread {

    private final Socket client;
    private final Logger logger;
    private final ServerInfos infos;
    private final Component kickReason;

    public FakeServer(Socket client, Logger logger, ServerInfos infos, Component kickReason) {
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
