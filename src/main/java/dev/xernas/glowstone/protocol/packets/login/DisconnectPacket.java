package dev.xernas.glowstone.protocol.packets.login;

import dev.xernas.glowstone.protocol.models.chat.Component;
import dev.xernas.glowstone.protocol.models.chat.ComponentSerializer;
import dev.xernas.glowstone.protocol.packets.IPacket;
import dev.xernas.glowstone.protocol.packets.utils.MCByteBuf;

import java.io.IOException;
import java.util.Map;

public class DisconnectPacket implements IPacket {

    private final Component reason;

    public DisconnectPacket(Component reason) {
        this.reason = reason;
    }

    @Override
    public int ID() {
        return 0x00;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        String jsonString = ComponentSerializer.serialize(reason).toString();
        byteBuf.writeString(jsonString, 262144);
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        return null;
    }
}
