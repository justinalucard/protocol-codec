package io.github.justinalucard.protocalcodec.protocols.llc;

import io.github.justinalucard.protocalcodec.annotations.Protocol;
import io.github.justinalucard.protocalcodec.core.ProtocolFragment;
import io.github.justinalucard.protocalcodec.naives.IntObjectCodec;
import io.github.justinalucard.protocalcodec.protocols.LengthField;
import io.github.justinalucard.protocalcodec.utils.CheckSumUtils;

public class PrincipalLlcProtocol<DataCodec extends  ProtocolFragment> extends ProtocolFragment implements LengthField {
    @Protocol(order = 0, length = 2)
    ProtocolFragment begin = new ProtocolFragment("AA55");
    @Protocol(order = 1, length = 2, isLengthField = true)
    IntObjectCodec length;
    @Protocol(order = 2, length = 1)
    ProtocolFragment command = new ProtocolFragment("90");
    @Protocol(order = 3, isGenericData = true, serializeLengthDetermination = LlcSerializeRequestDataLengthCalculator.class, deserializeLengthDetermination = LlcDeserializeRequestLengthCalculator.class)
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


    public PrincipalLlcProtocol(DataCodec codec, String commandHex) {
        setCommand(new ProtocolFragment(commandHex));
        setData(codec);
        setLength(new IntObjectCodec(data.getBytes().length + command.getBytes().length));
        setCheck(CheckSumUtils.lrc(command, getData()));
    }



    public PrincipalLlcProtocol(byte[] bytes){
        super(bytes);
    }
    public PrincipalLlcProtocol(String hexString){
        super(hexString);
    }
}
