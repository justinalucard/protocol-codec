package io.github.justinalucard.protocolcodec.protocols.llc.lamp;

import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.enums.LampColorEnum;
import io.github.justinalucard.protocolcodec.exceptions.IllegalProtocolException;
import io.github.justinalucard.protocolcodec.naives.IntObjectCodec;

/**
 * 变灯请求Data域的数据实体
 */
public class LampRequestData extends ProtocolFragment{

    @Protocol(order = 1, length = 1)
    LampColorObjectCodec LampColor;
    @Protocol(order = 2, length = 1)
    IntObjectCodec lightingCount;

    public LampRequestData(LampColorEnum lampColor, int lightingCount) {
        this.setLampColor(new LampColorObjectCodec(lampColor));
        this.setLightingCount(new IntObjectCodec(lightingCount));
    }

    public LampRequestData(byte[] bytes) {
        super(bytes);
    }

    public LampRequestData(String hexString) {
        super(hexString);
    }

    public LampColorObjectCodec getLampColor() {
        return LampColor;
    }

    public void setLampColor(LampColorObjectCodec lampColor) {
        LampColor = lampColor;
    }

    public IntObjectCodec getLightingCount() {
        return lightingCount;
    }

    public void setLightingCount(IntObjectCodec lightingCount) {
        if(lightingCount.getValue() < 1 || lightingCount.getValue() > 5){
            throw new IllegalProtocolException("亮灯数量只能为1-5，当前：" + lightingCount);
        }
        this.lightingCount = lightingCount;
    }



}
