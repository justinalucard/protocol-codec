package io.github.justinalucard.protocalcodec.protocols.llc.lamp;

import io.github.justinalucard.protocalcodec.core.ProtocolCodec;
import io.github.justinalucard.protocalcodec.enums.LampColorEnum;

import java.util.Arrays;

/**
 * 变灯Data域数据实体对应的编解码器
 */
public class LampRequestDataCodec extends ProtocolCodec<LampRequestData> {

    public LampRequestDataCodec(byte[] bytes) {
        super(bytes, LampRequestData.class);
    }

    public LampRequestDataCodec(LampRequestData lampRequestData) {
        super(lampRequestData, lampRequestData.getClass());
    }

    /**
     * 静态工厂方法，语法糖
     * @param lampColor 颜色
     * @param lightCount 亮灯数
     * @return
     */
    public static LampRequestDataCodec create(LampColorEnum lampColor, int lightCount){
        return new LampRequestDataCodec(new LampRequestData(lampColor, lightCount));
    }



    public static void main(String[] args) {
        LampRequestDataCodec requestData = new LampRequestDataCodec(
                new LampRequestData(LampColorEnum.RAINBOW, 3)
        );
        System.out.println(requestData.toString());
        System.out.println(Arrays.toString(requestData.getBytes()));
        System.out.println(requestData.getHexString());

        byte[] bytes = new byte[2];
        bytes[0] = 2;
        bytes[1] = 4;

        LampRequestDataCodec requestData2 = new LampRequestDataCodec(bytes);
        System.out.println(requestData2.getValue());
        System.out.println(Arrays.toString(requestData2.getBytes()));
        System.out.println(requestData2.getHexString());

    }

}
