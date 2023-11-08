package dev.xernas.fakesrv.server.utils;

import dev.xernas.fakesrv.protocol.packets.IPacket;
import java.net.Socket;
import java.util.Map;

public class Logger {

    private final String prefix;
    private final boolean enablePackets;
    private final boolean enableConnections;

    public Logger(String prefix, boolean enablePackets, boolean enableConnections) {
        this.prefix = ConsoleColors.YELLOW + prefix;
        this.enablePackets = enablePackets;
        this.enableConnections = enableConnections;
    }

    public void info(String s) {
        System.out.println(prefix + ConsoleColors.BLUE + " [INFO] " + s + ConsoleColors.RESET);
    }

    public void warn(String s) {
        System.out.println(prefix + ConsoleColors.RED + " [WARN] " + s + ConsoleColors.RESET);
    }

    public void connect(Socket socket) {
        if (enableConnections) {
            info("New connection: " + socket.getRemoteSocketAddress());
        }
    }
    public void disconnect(Socket socket) {
        if (enableConnections) {
            info(socket.getRemoteSocketAddress() + " disconnected.");
        }
    }

    public void packetIncoming(Map<String, Object> data) {
        if (enablePackets) {
            info("---Packet incoming---");
            data.forEach((key, value) -> {
                System.out.println(key + ": " + value + ConsoleColors.RESET);
            });
            filler();
        }
    }

    public void packetSent(IPacket packet) {
        if (enablePackets) {
            info("New packet sent: " + packet.ID() + "//" + packet.getClass().getSimpleName());
            filler();
        }
    }

    private void filler() {
        System.out.println("------------------------------------" + ConsoleColors.RESET);
    }

}
