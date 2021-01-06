package com.justin.protocal.codec.protocols.lamp;

import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.enums.LampColorEnum;
import com.justin.protocal.codec.exceptions.IllegalProtocolException;
import com.justin.protocal.codec.naives.IntProtocolCodec;
import com.justin.protocal.codec.naives.LampColorProtocolCodec;

/**
 * 变灯请求Data域的数据实体
 */
public class LampRequestData extends ProtocolFragment{

    @Protocol(order = 1, length = 1)
    LampColorProtocolCodec LampColor;
    @Protocol(order = 2, length = 1)
    IntProtocolCodec lightingCount;

    public LampRequestData(LampColorEnum lampColor, int lightingCount) {
        this.setLampColor(new LampColorProtocolCodec(lampColor));
        this.setLightingCount(new IntProtocolCodec(lightingCount));
    }

    public LampRequestData(byte[] bytes) {
        super(bytes);
    }

    public LampRequestData(String hexString) {
        super(hexString);
    }

    public LampColorProtocolCodec getLampColor() {
        return LampColor;
    }

    public void setLampColor(LampColorProtocolCodec lampColor) {
        LampColor = lampColor;
    }

    public IntProtocolCodec getLightingCount() {
        return lightingCount;
    }

    public void setLightingCount(IntProtocolCodec lightingCount) {
        if(lightingCount.getValue() < 1 || lightingCount.getValue() > 5){
            throw new IllegalProtocolException("亮灯数量只能为1-5，当前：" + lightingCount);
        }
        this.lightingCount = lightingCount;
    }



}
