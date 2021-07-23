package com.justin.protocal.codec.protocols.lamp;

import com.justin.protocal.codec.protocols.LengthField;
import com.justin.protocal.codec.protocols.PrincipalProtocol;

/**
 * 变灯请求整体协议实体
 */
public class LampRequestProtocol extends PrincipalProtocol<LampRequestDataCodec> {
    /**
     * 根据数据域编码器，创建一条变灯请求协议的语法糖
     *
     * @param codec
     * @return
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
