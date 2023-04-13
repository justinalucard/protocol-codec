package io.github.justinalucard.protocolcodec.protocols.t905;

import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.naives.Bcd8421ObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.LengthField;
import io.github.justinalucard.protocolcodec.utils.CheckSumUtils;

public class PrincipalT905Protocol<DataCodec extends  ProtocolFragment> extends ProtocolFragment implements LengthField {
    @Protocol(order = 0, length = 1)
    ProtocolFragment begin = new ProtocolFragment("7E");
    @Protocol(order = 1, length = 2)
    UInt16ObjectCodec messageId;
    @Protocol(order = 2, length = 2, isLengthField = true)
    UInt16ObjectCodec length;
    @Protocol(order = 3, length = 6)
    Bcd8421ObjectCodec isuId;
    @Protocol(order = 4, length = 2)
    UInt16ObjectCodec messageSerialNo;
    @Protocol(order = 5, isGenericData = true, serializeLengthDetermination = T905SerializeRequestDataLengthCalculator.class, deserializeLengthDetermination = T905DeserializeRequestLengthCalculator.class)
    DataCodec data;
    @Protocol(order = 6, length = 1)
    ProtocolFragment check;
    @Protocol(order = 7, length = 1)
    ProtocolFragment end = new ProtocolFragment("7E");

    public ProtocolFragment getBegin() {
        return begin;
    }

    public UInt16ObjectCodec getMessageId() {
        return messageId;
    }

    @Override
    public UInt16ObjectCodec getLength() {
        return length;
    }

    public Bcd8421ObjectCodec getIsuId() {
        return isuId;
    }

    public UInt16ObjectCodec getMessageSerialNo() {
        return messageSerialNo;
    }

    public DataCodec getData() {
        return data;
    }

    public ProtocolFragment getCheck() {
        return check;
    }

    public ProtocolFragment getEnd() {
        return end;
    }

    public PrincipalT905Protocol(int messageId, String isuId, int messageSerialNo, DataCodec codec) {
        this.messageId = new UInt16ObjectCodec(messageId);
        if(isuId.length()!=12)
            throw new RuntimeException("905 2014版协议中，isuid长度包含厂商标识必须为12位");
        this.isuId = new Bcd8421ObjectCodec(isuId);
        this.messageSerialNo = new UInt16ObjectCodec(messageSerialNo);
        this.data = codec;
        this.length = new UInt16ObjectCodec(getData().getBytes().length);
        this.check = CheckSumUtils.xor(getMessageId(), getLength(), getIsuId(), getMessageSerialNo(), getData());
    }



    public PrincipalT905Protocol(byte[] bytes){
        super(bytes);
    }
    public PrincipalT905Protocol(String hexString){
        super(hexString);
    }
}
