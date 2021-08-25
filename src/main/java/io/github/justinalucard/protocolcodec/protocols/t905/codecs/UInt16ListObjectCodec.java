package io.github.justinalucard.protocolcodec.protocols.t905.codecs;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class UInt16ListObjectCodec extends ObjectCodec<int[]> {
    public UInt16ListObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public UInt16ListObjectCodec(String hexString) {
        super(hexString);
    }

    public UInt16ListObjectCodec(int[] ints) {
        super(ints);
    }

    protected int[] deserialize() {
        byte[] bytes = this.getBytes();
        List<Integer> ret = new ArrayList<>();

        for (int i = 0; i < bytes.length; i += 2) {
            byte[] temp = new byte[2];
            System.arraycopy(bytes, i, temp, 0, 2);
            ret.add(new UInt16ObjectCodec(temp).getValue());
        }

        return ret.stream().mapToInt(Integer::intValue).toArray();

    }

    protected UInt16ListObjectCodec serialize() {
        byte[] ret = new byte[0];
        if(getValue() != null) {
            for (int i : getValue()) {
                ret = ArrayUtils.addAll(ret, new UInt16ObjectCodec(i).getBytes());
            }
        }
        return new UInt16ListObjectCodec(ret);
    }
}
