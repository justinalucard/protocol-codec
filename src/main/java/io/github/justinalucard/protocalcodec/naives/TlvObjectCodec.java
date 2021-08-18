package io.github.justinalucard.protocalcodec.naives;

import io.github.justinalucard.protocalcodec.core.ObjectCodec;
import io.github.justinalucard.protocalcodec.core.ProtocolFragment;
import io.github.justinalucard.protocalcodec.utils.BufferUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.*;

/**
 * TLV编码器
 */
public class TlvObjectCodec
        <TCodec extends ObjectCodec<?>,
                LCodec extends ObjectCodec<Integer>,
                VCodec extends ObjectCodec<?>>
        extends ObjectCodec<Map<TCodec, VCodec>> {

    private int tLength;

    private int lLength;

    public TlvObjectCodec(byte[] bytes, int tLength, int lLength) {
        setLength(tLength, lLength);
        fromBytes(bytes);
    }

    public TlvObjectCodec(Map<TCodec, VCodec> value, int tLength, int lLength) {
        setLength(tLength, lLength);
        fromValue(value);
    }


    public TlvObjectCodec(String hexString, int tLength, int lLength) {
        setLength(tLength, lLength);
        fromHexString(hexString);
    }

    private void setLength(int tLength, int lLength) {
        this.tLength = tLength;
        this.lLength = lLength;
    }

    @SuppressWarnings("all")
    @Override
    protected Map<TCodec, VCodec> deserialize() {
        Map<TCodec, VCodec> ret = new HashMap<>();
        int offset = 0;
        byte[] theBytes = getBytes();
        while (offset < theBytes.length) {

            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();

            Type[] typeArray = parameterizedType.getActualTypeArguments();

            try {
                TCodec tCodec = ((Class<TCodec>) typeArray[0]).getConstructor(byte[].class).newInstance(Arrays.copyOfRange(theBytes, offset, offset += tLength));
                LCodec lCodec = ((Class<LCodec>) typeArray[1]).getConstructor(byte[].class).newInstance(Arrays.copyOfRange(theBytes, offset, offset += lLength));
                VCodec vCodec = ((Class<VCodec>) typeArray[2]).getConstructor(byte[].class).newInstance(Arrays.copyOfRange(theBytes, offset, offset += lCodec.getValue()));

                ret.put(tCodec, vCodec);

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        return ret;
    }

    @SuppressWarnings("all")
    @Override
    protected ProtocolFragment serialize() {

        byte[] ret = new byte[0];

        if (getValue() != null && !getValue().isEmpty()) {

            for (Map.Entry<TCodec, VCodec> entry : getValue().entrySet()) {
                TCodec tag = entry.getKey();
                VCodec v = entry.getValue();


                ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();

                Type[] typeArray = parameterizedType.getActualTypeArguments();
                try {

                    int originalLength = ret.length;

                    int finalLength = originalLength + tLength + lLength + v.getBytes().length;

                    ret = Arrays.copyOf(ret, finalLength);

                    System.arraycopy(BufferUtils.fixLengthPaddingLeft(tag.getBytes(), tLength), 0, ret, originalLength, tLength);

                    LCodec lCodec = ((Class<LCodec>) typeArray[1]).getConstructor(Integer.class).newInstance(v.getBytes().length);

                    System.arraycopy(BufferUtils.fixLengthPaddingLeft(lCodec.getBytes(), lLength), 0,  ret, originalLength + tLength, lLength);
                    System.arraycopy(v.getBytes(), 0, ret, originalLength + tLength + lLength, v.getBytes().length);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

        return new ProtocolFragment(ret);

    }
}
