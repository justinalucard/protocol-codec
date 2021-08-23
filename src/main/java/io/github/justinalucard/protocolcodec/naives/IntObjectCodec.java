package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.utils.ConverterUtils;


import java.util.Arrays;


/**
 * 高位在前的整数编解码器
 */
public class IntObjectCodec extends ObjectCodec<Integer> {



    public IntObjectCodec(byte[] bytes) {
        super(bytes, Integer.class);
    }

    public IntObjectCodec(String hexString) {
        super(hexString, Integer.class);
    }

    public IntObjectCodec(Integer integer) {
        super(integer, Integer.class);
    }

    @Override
    protected Integer deserialize() {
        byte[] bytes = getBytes();
        return ConverterUtils.getInt(bytes);
    }

    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue()));
        }
        return null;
    }


    public static void main(String[] args) {
        IntObjectCodec intProtocolNode = new IntObjectCodec("FFFF");
        System.out.println(intProtocolNode.getValue());
        System.out.println(intProtocolNode.getHexString());
        System.out.println(Arrays.toString(intProtocolNode.getBytes()));
        IntObjectCodec intProtocolNode2 = new IntObjectCodec(65535);
        System.out.println(intProtocolNode2.getHexString());
        System.out.println(Arrays.toString(intProtocolNode2.getBytes()));
        System.out.println(intProtocolNode2.getValue());
    }

}
