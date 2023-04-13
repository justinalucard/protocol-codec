package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array编码器，包含N个相同类型的Codec。比如ArrayObjectCodec<UInt32ObjectCodec>，表示有N个UInt32编码器
 */
public class ArrayObjectCodec
        <ElementCodec extends ObjectCodec<?>>
        extends ObjectCodec<List<ElementCodec>> {

    private int perSize;

    public ArrayObjectCodec(byte[] bytes, int perSize) {
        this.perSize = perSize;
        fromBytes(bytes);
    }

    public ArrayObjectCodec(List<ElementCodec> value, int perSize) {
        this.perSize = perSize;
        fromValue(value);
    }


    public ArrayObjectCodec(String hexString, int perSize) {
        this.perSize = perSize;
        fromHexString(hexString);
    }

    @SuppressWarnings("all")
    @Override
    protected List<ElementCodec> deserialize() {
        List<ElementCodec> ret = new ArrayList<>();
        byte[] theBytes = getBytes();

        int offset = 0;


        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();

        Type[] typeArray = parameterizedType.getActualTypeArguments();

        while (offset < theBytes.length){
            try {
              ElementCodec codec =  ((Class<ElementCodec>) typeArray[0]).getConstructor(byte[].class).newInstance(Arrays.copyOfRange(theBytes, offset, offset += perSize));
              ret.add(codec);

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        return ret;
    }

    @Override
    protected ProtocolFragment serialize() {
        byte[] ret = new byte[0];
        int offset = 0;
        int finalSize = 0;
        if (getValue() != null && !getValue().isEmpty()) {
            for (ElementCodec elementCodec : getValue()) {
                finalSize += perSize;
                ret = Arrays.copyOf(ret, finalSize);
                System.arraycopy(elementCodec.getBytes(), 0, ret, offset, perSize);
                offset += perSize;
            }
        }

        return new ProtocolFragment(ret);
    }
}
