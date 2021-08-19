package io.github.justinalucard.protocalcodec.protocols.llc.lamp;

import io.github.justinalucard.protocalcodec.enums.LampColorEnum;
import io.github.justinalucard.protocalcodec.protocols.llc.PrincipalLlcProtocolCodec;

/**
 * 变灯请求协议的编解码器
 */
public class LampRequestProtocolCodec extends PrincipalLlcProtocolCodec<LampRequestProtocol> {

    public LampRequestProtocolCodec(byte[] bytes) {
        super(bytes, LampRequestProtocol.class);
    }

    public LampRequestProtocolCodec(LampRequestProtocol data) {
        super(data, LampRequestProtocol.class);
    }

    /**
     * 创建工厂语法糖
     * @param lampColor 颜色
     * @param lightCount 亮灯数
     * @return LampRequestProtocolCodec协议解码器
     */
    public static LampRequestProtocolCodec create(LampColorEnum lampColor, int lightCount) {
        return new LampRequestProtocolCodec(LampRequestProtocol.create(LampRequestDataCodec.create(lampColor, lightCount)));
    }



    public static void main(String[] args) {

        LampRequestProtocolCodec serializer = LampRequestProtocolCodec.create(LampColorEnum.RAINBOW, 3);

        LampRequestProtocolCodec lampRequestProtocolCodec = new LampRequestProtocolCodec(serializer.getBytes());


        System.out.println(serializer.getHexString());
    }


}
