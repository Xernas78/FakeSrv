package dev.xernas.amethystfake.protocol.packets.login;

import dev.xernas.amethystfake.protocol.models.chat.Component;
import dev.xernas.amethystfake.protocol.models.chat.ComponentSerializer;
import dev.xernas.amethystfake.protocol.packets.IPacket;
import dev.xernas.amethystfake.protocol.packets.utils.MCByteBuf;

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
