package com.justin.protocal.codec.protocols.lamp;

import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.core.ObjectCodec;
import com.justin.protocal.codec.exceptions.IllegalProtocolException;
import com.justin.protocal.codec.enums.LampColorEnum;
import com.justin.protocal.codec.utils.ConverterUtils;

import java.util.Arrays;
import java.util.Optional;


/**
 * 变灯枚举的编解码器
 */
public class LampColorObjectCodec extends ObjectCodec<LampColorEnum> {


    public LampColorObjectCodec(byte[] bytes) {
        super(bytes, LampColorEnum.class);
    }

    public LampColorObjectCodec(String hexString) {
        super(hexString, LampColorEnum.class);
    }

    public LampColorObjectCodec(LampColorEnum lampColorEnum) {
        super(lampColorEnum, LampColorEnum.class);
    }

    @Override
    protected LampColorEnum deserialize() {
        byte[] bytes = getBytes();
        int color = ConverterUtils.getInt(bytes);
        Optional<LampColorEnum> lampColor =
                Arrays.stream(LampColorEnum.values())
                        .filter(x -> x.getCode() == color)
                .findFirst();

        return lampColor.orElseThrow(()-> new IllegalProtocolException("亮灯数量只能为1-5，当前：" + color));

    }

    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue().getCode()));
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
