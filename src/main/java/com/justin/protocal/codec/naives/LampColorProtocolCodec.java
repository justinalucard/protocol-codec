package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.exceptions.IllegalProtocolException;
import com.justin.protocal.codec.enums.LampColorEnum;
import com.justin.protocal.codec.utils.ConverterUtils;

import java.util.Arrays;
import java.util.Optional;


/**
 * 变灯枚举的编解码器
 */
public class LampColorProtocolCodec extends ProtocolCodec<LampColorEnum> {


    public LampColorProtocolCodec(byte[] bytes) {
        super(bytes);
    }

    public LampColorProtocolCodec(String hexString) {
        super(hexString);
    }

    public LampColorProtocolCodec(LampColorEnum lampColorEnum) {
        super(lampColorEnum);
    }

    @Override
    protected LampColorEnum deserialize() {
        byte[] bytes = getBytes();
        int color = ConverterUtils.byteArrayToInt(bytes);
        Optional<LampColorEnum> lampColor =
                Arrays.stream(LampColorEnum.values())
                        .filter(x -> x.getCode() == color)
                .findFirst();

        return lampColor.orElseThrow(()-> new IllegalProtocolException("亮灯数量只能为1-5，当前：" + color));

    }

    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.intToByteArray(this.getValue().getCode()));
        }
        return null;
    }




    public static void main(String[] args) {
//        LampColorProtocolNode intProtocolNode = new LampColorProtocolNode("1FFFF");
//        System.out.println(intProtocolNode.getValue());
//        System.out.println(intProtocolNode.getHexString());
//        System.out.println(Arrays.toString(intProtocolNode.getBytes()));
//        LampColorProtocolNode intProtocolNode2 = new LampColorProtocolNode(165535);
//        System.out.println(intProtocolNode2.getHexString());
//        System.out.println(Arrays.toString(intProtocolNode2.getBytes()));
//        System.out.println(intProtocolNode2.getValue());
    }

}
