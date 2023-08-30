package dev.xernas.glowstone.protocol.packets.status;

import dev.xernas.glowstone.protocol.packets.IPacket;
import dev.xernas.glowstone.protocol.packets.utils.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PingPongPacket implements IPacket {

    private Long time;

    public PingPongPacket() {};
    public PingPongPacket(Long time) {
        this.time = time;
    }

    @Override
    public int ID() {
        return 0x01;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeLong(time);
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("time", byteBuf.readLong());
        return response;
    }
}
