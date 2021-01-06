package com.justin.protocal.codec.protocols.lamp;

import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.naives.IntProtocolCodec;
import com.justin.protocal.codec.protocols.DeserializeRequestLengthCalculator;
import com.justin.protocal.codec.protocols.LengthField;
import com.justin.protocal.codec.protocols.SerializeRequestDataLengthCalculator;
import com.justin.protocal.codec.utils.CheckSumUtils;

/**
 * 变灯请求整体协议实体
 */
public class LampRequestProtocol extends ProtocolFragment implements LengthField {
    /**
     * 根据数据域编码器，创建一条变灯请求协议的语法糖
     * @param codec
     * @return
     */
    public static LampRequestProtocol create(LampRequestDataCodec codec){
        LampRequestProtocol ret = new LampRequestProtocol();
        ret.setData(codec);
        ret.setLength(new IntProtocolCodec( ret.data.getBytes().length + ret.command.getBytes().length));
        ret.setCheck(CheckSumUtils.lrc(ret.command, ret.getData()));
        return ret;
    }

    @Protocol(order = 0, length = 2)
    ProtocolFragment begin = new ProtocolFragment("AA55");
    @Protocol(order = 1, length = 2, isLengthField = true)
    IntProtocolCodec length;
    @Protocol(order = 2, length = 1)
    ProtocolFragment command = new ProtocolFragment("90");
    @Protocol(order = 3, serializeLengthDetermination = SerializeRequestDataLengthCalculator.class, deserializeLengthDetermination = DeserializeRequestLengthCalculator.class)
    LampRequestDataCodec data;
    @Protocol(order = 4, length = 1)
    ProtocolFragment check;
    @Protocol(order = 5, length = 2)
    ProtocolFragment end = new ProtocolFragment("AAEF");

    public LampRequestProtocol(byte[] bytes) {
        super(bytes);
    }

    public LampRequestProtocol(String hexString) {
        super(hexString);
    }

    public LampRequestProtocol() {
    }


    public ProtocolFragment getBegin() {
        return begin;
    }

    public void setBegin(ProtocolFragment begin) {
        this.begin = begin;
    }

    public IntProtocolCodec getLength() {
        return length;
    }

    public void setLength(IntProtocolCodec length) {
        this.length = length;
    }

    public ProtocolFragment getCommand() {
        return command;
    }

    public void setCommand(ProtocolFragment command) {
        this.command = command;
    }

    public LampRequestDataCodec getData() {
        return data;
    }

    public void setData(LampRequestDataCodec data) {
        this.data = data;
    }

    public ProtocolFragment getCheck() {
        return check;
    }

    public void setCheck(ProtocolFragment check) {
        this.check = check;
    }

    public ProtocolFragment getEnd() {
        return end;
    }

    public void setEnd(ProtocolFragment end) {
        this.end = end;
    }
}



//
//
//
//public class LampRequestProtocol extends RequestProtocol<LampRequestDataCodec> {
//    public static LampRequestProtocol create(LampRequestDataCodec codec){
//        LampRequestProtocol lampRequestProtocol = new LampRequestProtocol();
//        lampRequestProtocol.setData(codec);
//        lampRequestProtocol.setCommand(new ProtocolFragment("90"));
//        lampRequestProtocol.setLength(new IntProtocolCodec( lampRequestProtocol.data.getBytes().length + lampRequestProtocol.command.getBytes().length));
//        lampRequestProtocol.setCheck(new ProtocolFragment("01"));
//        return lampRequestProtocol;
////        return (LampRequestProtocol)RequestProtocol.create(new ProtocolFragment("90"),
////                LampRequestDataCodec.create(lampColor, lightCount)
////        );
//    }
//
//    public LampRequestProtocol(byte[] bytes) {
//        super(bytes);
//    }
//
//    public LampRequestProtocol(String hexString) {
//        super(hexString);
//    }
//
//    public LampRequestProtocol() {
//    }
//
//    @Override
//    public LampRequestDataCodec getData() {
//        return super.getData();
//    }
//
//    @Override
//    public void setData(LampRequestDataCodec data) {
//        super.setData(data);
//    }
//}
