package dev.xernas.amethystfake.protocol.packets.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MCByteBuf {

    private final int SEGMENT_BITS = 0x7F;
    private final int CONTINUE_BIT = 0x80;

    public final DataOutputStream OUT;
    public final DataInputStream IN;

    public MCByteBuf(DataOutputStream out, DataInputStream in) {
        this.OUT = out;
        this.IN = in;
    }

    public void flush() throws IOException {
        OUT.flush();
    }

    public void write(byte[] bytes) throws IOException {
        OUT.write(bytes);
    }

    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                OUT.writeByte(value);
                return;
            }

            OUT.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public int readVarInt() throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = IN.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public void writeString(String s, int i) throws IOException {
        if (s.length() > i) {
            int j = s.length();

            System.out.println("String too big (was " + j + " characters, max " + i + ")");
        } else {
            byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
            int k = i * 3;

            if (abyte.length > k) {
                System.out.println("String too big (was " + abyte.length + " bytes encoded, max " + k + ")");
            } else {
                writeVarInt(abyte.length);
                OUT.write(abyte);
            }
        }
    }

    public String readString(int limit) throws IOException {
        int size = readVarInt();
        if (size > limit * 4) {
            System.out.println("String too long (" + size + " > " + limit + " * 4)");
        }
        byte[] stringBytes = new byte[size];
        IN.readFully(stringBytes);
        String string = new String(stringBytes);
        if (string.length() > limit) {
            System.out.println("String too long (" + string.length() + " > " + limit + ")");
        }
        return string;
    }

    public void writeUUID(UUID uuid) throws IOException {
        OUT.writeLong(uuid.getMostSignificantBits());
        OUT.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID() throws IOException {
        return new UUID(IN.readLong(), IN.readLong());
    }

    public void writeProperty(String name, String value) throws IOException {
        writeString(name, 32767);
        writeString(name, 32767);
        OUT.writeBoolean(false);
    }

    public void writeByte(byte bytes) throws IOException {
        OUT.writeByte(bytes);
    }

    public void writeByte(int integer) throws IOException {
        OUT.writeByte(integer);
    }

    public Byte readByte() throws IOException {
        return IN.readByte();
    }

    public void writeLong(Long longs) throws IOException {
        OUT.writeLong(longs);
    }

    public Long readLong() throws IOException {
        return IN.readLong();
    }

    public void writeBoolean(boolean bool) throws IOException {
        OUT.writeBoolean(bool);
    }

    public boolean readBoolean() throws IOException {
        return IN.readBoolean();
    }

    public void writeShort(Short shorts) throws IOException {
        OUT.writeShort(shorts);
    }

    public Short readShort() throws IOException {
        return IN.readShort();
    }

    public void writeInt(Integer integer) throws IOException {
        OUT.writeInt(integer);
    }

    public Integer readInt() throws IOException {
        return IN.readInt();
    }

}
