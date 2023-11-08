package dev.xernas.fakesrv.server;

import dev.xernas.fakesrv.protocol.models.ServerInfos;
import dev.xernas.fakesrv.protocol.models.chat.Component;
import dev.xernas.fakesrv.server.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final Logger logger;
    private final int port;
    private final ServerInfos infos;
    private final Component kickReason;
    private final ServerSocket socket;

    public Server(Logger logger, int port, ServerInfos infos, Component kickReason) throws IOException {
        this.logger = logger;
        this.port = port;
        this.infos = infos;
        this.kickReason = kickReason;
        this.socket = new ServerSocket(port, infos.getPlayers().getMax());
    }

    public void start() throws IOException {
        logger.info("Listening on port " + port);
        while (true) {
            Socket client = socket.accept();
            logger.connect(client);

            new FakeServer(client, logger, infos, kickReason).start();
        }
    }

    public void stop() throws IOException {
        socket.close();
    }

    public int getPort() {
        return port;
    }

    public ServerInfos getInfos() {
        return infos;
    }

    public ServerSocket getSocket() {
        return socket;
    }
}
