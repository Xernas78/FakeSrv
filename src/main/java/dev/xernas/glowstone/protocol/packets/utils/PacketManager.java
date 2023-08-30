package dev.xernas.glowstone.protocol.packets.utils;

import dev.xernas.glowstone.protocol.models.ServerInfos;
import dev.xernas.glowstone.protocol.packets.IHandler;
import dev.xernas.glowstone.protocol.packets.IPacket;
import dev.xernas.glowstone.server.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class PacketManager {

    private final MCByteBuf byteBuf;
    private final Socket client;
    private final Logger logger;
    private final ServerInfos infos;

    public PacketManager(MCByteBuf byteBuf, Socket client, Logger logger, ServerInfos infos) {
        this.byteBuf = byteBuf;
        this.client = client;
        this.logger = logger;
        this.infos = infos;
    }

    public void sendPacket(IPacket packet) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        MCByteBuf mcByteBuf = new MCByteBuf(dataOutputStream, byteBuf.IN);
        mcByteBuf.writeVarInt(packet.ID());
        packet.write(mcByteBuf);
        byteBuf.writeVarInt(byteArrayOutputStream.size());
        byteBuf.write(byteArrayOutputStream.toByteArray());
        logger.packetSent(packet);
        byteBuf.flush();
    }

    public Map<String, Object> waitForPacket(IPacket packet) throws IOException {
        int size = byteBuf.readVarInt();
        int id = byteBuf.readVarInt();
        Map<String, Object> response = packet.read(byteBuf);
        if (response != null) {
            if (id == packet.ID()) {
                response.put("size", size);
                response.put("id", id);
                logger.packetIncoming(response);
                return response;
            } else {
                return waitForPacket(packet);
            }
        }
        return null;
    }

    public Socket getClient() {
        return client;
    }

    public Logger getLogger() {
        return logger;
    }

    public ServerInfos getInfos() {
        return infos;
    }

    public void nextState(IHandler stateHandler) throws IOException {
        stateHandler.handle();
    }
}
