package dev.xernas.fakesrv.protocol.packets;

import java.io.IOException;
import java.util.Map;

import dev.xernas.fakesrv.protocol.packets.utils.MCByteBuf;

public interface IPacket {

    int ID();
    void write(MCByteBuf byteBuf) throws IOException;
    Map<String, Object> read(MCByteBuf byteBuf) throws IOException;

}
