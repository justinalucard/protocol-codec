package com.justin.protocal.codec.protocols;

import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.naives.IntObjectCodec;
import com.justin.protocal.codec.utils.CheckSumUtils;

import java.lang.annotation.Repeatable;

public class PrincipalProtocol<DataCodec extends  ProtocolFragment> extends ProtocolFragment implements LengthField {
    @Protocol(order = 0, length = 2)
    ProtocolFragment begin = new ProtocolFragment("AA55");
    @Protocol(order = 1, length = 2, isLengthField = true)
    IntObjectCodec length;
    @Protocol(order = 2, length = 1)
    ProtocolFragment command = new ProtocolFragment("90");
    @Protocol(order = 3, isGenericData = true, serializeLengthDetermination = SerializeRequestDataLengthCalculator.class, deserializeLengthDetermination = DeserializeRequestLengthCalculator.class)
    DataCodec data;
    @Protocol(order = 4, length = 1)
    ProtocolFragment check;
    @Protocol(order = 5, length = 2)
    ProtocolFragment end = new ProtocolFragment("AAEF");

    public ProtocolFragment getBegin() {
        return begin;
    }

    public void setBegin(ProtocolFragment begin) {
        this.begin = begin;
    }

    public IntObjectCodec getLength() {
        return length;
    }

    public void setLength(IntObjectCodec length) {
        this.length = length;
    }

    public ProtocolFragment getCommand() {
        return command;
    }

    public void setCommand(ProtocolFragment command) {
        this.command = command;
    }

    public DataCodec getData() {
        return data;
    }

    public void setData(DataCodec data) {
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


    public PrincipalProtocol(DataCodec codec, String commandHex) {
        setCommand(new ProtocolFragment(commandHex));
        setData(codec);
        setLength(new IntObjectCodec(data.getBytes().length + command.getBytes().length));
        setCheck(CheckSumUtils.lrc(command, getData()));
    }



    public PrincipalProtocol(byte[] bytes){
        super(bytes);
    }
    public PrincipalProtocol(String hexString){
        super(hexString);
    }
}
