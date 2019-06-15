package model.utils;// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class UUID implements Serializable, Comparable<UUID>, Cloneable {
    private static final long serialVersionUID = -8100510331328784120L;
    private long mostSigBits;
    private long leastSigBits;
    private transient volatile long timestamp = -1L;
    private transient int hashCode = -1;
    private static volatile SecureRandom numberGenerator = null;
    private static char[] bits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w'};
    private String _32 = null;

    private UUID(byte[] data) {
        long msb = 0L;
        long lsb = 0L;

        assert data.length == 16;

        int i;
        for(i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 255);
        }

        for(i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 255);
        }

        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    public UUID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    public static UUID create() {
        return randomUUID();
    }

    public static UUID randomUUID() {
        SecureRandom ng = numberGenerator;
        if(ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] = (byte)(randomBytes[6] & 15);
        randomBytes[6] = (byte)(randomBytes[6] | 64);
        randomBytes[8] = (byte)(randomBytes[8] & 63);
        randomBytes[8] = (byte)(randomBytes[8] | 128);
        return new UUID(randomBytes);
    }

    public static UUID nameUUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var3) {
            throw new InternalError("MD5 not supported");
        }

        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] = (byte)(md5Bytes[6] & 15);
        md5Bytes[6] = (byte)(md5Bytes[6] | 48);
        md5Bytes[8] = (byte)(md5Bytes[8] & 63);
        md5Bytes[8] = (byte)(md5Bytes[8] | 128);
        return new UUID(md5Bytes);
    }

    public static UUID fromString(String name) {
        String[] components = name.split("-");
        if(components.length != 5) {
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        } else {
            for(int mostSigBits = 0; mostSigBits < 5; ++mostSigBits) {
                components[mostSigBits] = "0x" + components[mostSigBits];
            }

            long var6 = Long.decode(components[0]).longValue();
            var6 <<= 16;
            var6 |= Long.decode(components[1]).longValue();
            var6 <<= 16;
            var6 |= Long.decode(components[2]).longValue();
            long leastSigBits = Long.decode(components[3]).longValue();
            leastSigBits <<= 48;
            leastSigBits |= Long.decode(components[4]).longValue();
            return new UUID(var6, leastSigBits);
        }
    }

    public long getLeastSignificantBits() {
        return this.leastSigBits;
    }

    public long getMostSignificantBits() {
        return this.mostSigBits;
    }

    public int version() {
        return (int)(this.mostSigBits >> 12) & 15;
    }

    public int variant() {
        return this.leastSigBits >>> 63 == 0L?0:(this.leastSigBits >>> 62 == 2L?2:(int)(this.leastSigBits >>> 61));
    }

    public long timestamp() {
        if(this.version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        } else {
            long result = this.timestamp;
            if(result < 0L) {
                result = (this.mostSigBits & 4095L) << 48;
                result |= (this.mostSigBits >> 16 & 65535L) << 32;
                result |= this.mostSigBits >>> 32;
                this.timestamp = result;
            }

            return result;
        }
    }

    public int clockSequence() {
        if(this.version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        } else {
            return (int)((this.leastSigBits & 4611404543450677248L) >>> 48);
        }
    }

    public long node() {
        if(this.version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        } else {
            return this.leastSigBits & 281474976710655L;
        }
    }

    public String toString() {
        return this.to32String();
    }

    private String to32String() {
        if(this._32 == null) {
            this._32 = convert32(this.mostSigBits >> 24);
            this._32 = this._32 + convert32(this.leastSigBits >> 24);
        }

        return this._32;
    }

    private static String convert32(long num) {
        char[] chars = new char[8];

        for(int i = 0; i < 8; ++i) {
            chars[i] = bits[(int)(num & 31L)];
            num >>= 5;
        }

        return new String(chars);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << digits * 4;
        return Long.toHexString(hi | val & hi - 1L).substring(1);
    }

    public int hashCode() {
        if(this.hashCode == -1) {
            this.hashCode = (int)(this.mostSigBits >> 32 ^ this.mostSigBits ^ this.leastSigBits >> 32 ^ this.leastSigBits);
        }

        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof UUID)) {
            return false;
        } else {
            UUID id = (UUID)obj;
            return this.mostSigBits == id.mostSigBits && this.leastSigBits == id.leastSigBits;
        }
    }

    public int compareTo(UUID val) {
        return this.mostSigBits < val.mostSigBits?-1:(this.mostSigBits > val.mostSigBits?1:(this.leastSigBits < val.leastSigBits?-1:(this.leastSigBits > val.leastSigBits?1:0)));
    }

    public byte[] toByteArray() {
        byte[] array = new byte[16];
        toBytes(this.mostSigBits, array, 0);
        toBytes(this.leastSigBits, array, 8);
        return array;
    }

    private static void toBytes(long x, byte[] array, int startPos) {
        int bytePos = 8;

        while(true) {
            --bytePos;
            if(bytePos < 0) {
                return;
            }

            array[startPos + bytePos] = (byte)((int)(x & 255L));
            x >>>= 8;
        }
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(this.mostSigBits);
        out.writeLong(this.leastSigBits);
    }

    public static UUID read(DataInput in) throws IOException {
        long mostSigBits = in.readLong();
        long leastSigBits = in.readLong();
        return new UUID(mostSigBits, leastSigBits);
    }

    public Object clone() {
        try {
            UUID e = (UUID)super.clone();
            e.mostSigBits = this.mostSigBits;
            e.leastSigBits = this.leastSigBits;
            return e;
        } catch (CloneNotSupportedException var2) {
            throw new InternalError();
        }
    }
}
