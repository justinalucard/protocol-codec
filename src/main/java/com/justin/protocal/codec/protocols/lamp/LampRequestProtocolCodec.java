package com.justin.protocal.codec.protocols.lamp;

import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.enums.LampColorEnum;
import com.justin.protocal.codec.protocols.CheckSumVerifier;

/**
 * 变灯请求协议的编解码器
 */
public class LampRequestProtocolCodec extends ProtocolCodec<LampRequestProtocol> {

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
     * @return
     */
    public static LampRequestProtocolCodec create(LampColorEnum lampColor, int lightCount) {
        return new LampRequestProtocolCodec(LampRequestProtocol.create(LampRequestDataCodec.create(lampColor, lightCount)));
    }

    @Override
    protected void afterDeserialize(LampRequestProtocol ret) {
        CheckSumVerifier.check(ret.getCheck(), ret.getCommand(), ret.getData());
    }

    public static void main(String[] args) {

        LampRequestProtocolCodec serializer = LampRequestProtocolCodec.create(LampColorEnum.RAINBOW, 3);

        LampRequestProtocolCodec lampRequestProtocolCodec = new LampRequestProtocolCodec(serializer.getBytes());


        System.out.println(serializer.getHexString());
    }


}
