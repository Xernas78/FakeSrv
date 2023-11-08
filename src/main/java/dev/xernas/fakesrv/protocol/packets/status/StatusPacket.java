package dev.xernas.fakesrv.protocol.packets.status;

import com.google.gson.Gson;
import dev.xernas.fakesrv.protocol.models.ServerInfos;
import dev.xernas.fakesrv.protocol.packets.IPacket;
import dev.xernas.fakesrv.protocol.packets.utils.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatusPacket implements IPacket {

    private ServerInfos infos;

    public StatusPacket() {};
    public StatusPacket(ServerInfos infos) {
        this.infos = infos;
    };

    @Override
    public int ID() {
        return 0x00;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(infos, ServerInfos.class);
        byteBuf.writeString(jsonString, 32767);
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        return new HashMap<>();
    }
}
