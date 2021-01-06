package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.utils.ConverterUtils;


import java.util.Arrays;


/**
 * 高位在前的整数编解码器
 */
public class IntProtocolCodec extends ProtocolCodec<Integer> {



    public IntProtocolCodec(byte[] bytes) {
        super(bytes);
    }

    public IntProtocolCodec(String hexString) {
        super(hexString);
    }

    public IntProtocolCodec(Integer integer) {
        super(integer);
    }

    @Override
    protected Integer deserialize() {
        byte[] bytes = getBytes();

        return ConverterUtils.byteArrayToInt(bytes);
    }

    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.intToByteArray(this.getValue()));
        }
        return null;
    }


    public static void main(String[] args) {
        IntProtocolCodec intProtocolNode = new IntProtocolCodec("FFFF");
        System.out.println(intProtocolNode.getValue());
        System.out.println(intProtocolNode.getHexString());
        System.out.println(Arrays.toString(intProtocolNode.getBytes()));
        IntProtocolCodec intProtocolNode2 = new IntProtocolCodec(65535);
        System.out.println(intProtocolNode2.getHexString());
        System.out.println(Arrays.toString(intProtocolNode2.getBytes()));
        System.out.println(intProtocolNode2.getValue());
    }

}
