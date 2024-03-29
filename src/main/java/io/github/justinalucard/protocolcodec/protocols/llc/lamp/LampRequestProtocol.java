package io.github.justinalucard.protocolcodec.protocols.llc.lamp;

import io.github.justinalucard.protocolcodec.protocols.llc.PrincipalLlcProtocol;

/**
 * 变灯请求整体协议实体
 */
public class LampRequestProtocol extends PrincipalLlcProtocol<LampRequestDataCodec> {
    /**
     * 根据数据域编码器，创建一条变灯请求协议的语法糖
     *
     * @param codec LampRequestDataCodec数据解码器
     * @return LampRequestProtocol协议解码器
     */
    public static LampRequestProtocol create(LampRequestDataCodec codec) {
        return new LampRequestProtocol(codec);
    }


    public LampRequestProtocol(byte[] bytes) {
        super(bytes);
    }

    public LampRequestProtocol(String hexString) {
        super(hexString);
    }

    public LampRequestProtocol(LampRequestDataCodec codec) {
        super(codec, "80");
    }


}
