package dev.xernas.amethystfake.protocol.packets.handshake;

import dev.xernas.amethystfake.protocol.packets.IPacket;
import dev.xernas.amethystfake.protocol.packets.utils.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandshakePacket implements IPacket {
    @Override
    public int ID() {
        return 0x00;
    }

    @Override
    public void write(MCByteBuf byteBuf) {

    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        Map<String, Object> map = new HashMap<>();
        int protocol = byteBuf.readVarInt();
        String host = byteBuf.readString(32767);
        Short port = byteBuf.readShort();
        int state = byteBuf.readVarInt();
        map.put("protocol", protocol);
        map.put("host", host);
        map.put("port", port);
        map.put("state", state);
        return map;
    }
}
