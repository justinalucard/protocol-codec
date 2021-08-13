import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.naives.UInt16ObjectCodec;
import com.justin.protocal.codec.naives.UInt8ObjectCodec;
import com.justin.protocal.codec.protocols.t905.PrincipalT905Protocol;
import org.junit.Test;

public class T905Test {

    @Test
    public void testT8001(){
        byte[] bytes = new byte[2];
        bytes[0] = 1;
        bytes[1] = 2;
        new UInt16ObjectCodec(bytes);

        T8001Protocol.Data data = new T8001Protocol.Data(1, 2, 3);
        T8001Protocol.Data.Codec dataCodec = new T8001Protocol.Data.Codec(data);
        System.out.println(dataCodec);
        System.out.println(dataCodec.getValue().getResponseMessageId().getValue());
        T8001Protocol t8001Protocol = new T8001Protocol("101234567890", 100, dataCodec);
        T8001Protocol.Codec codec = new T8001Protocol.Codec(t8001Protocol);
        System.out.println(codec);
        System.out.println(codec.getValue().getIsuId().getValue());


        T8001Protocol.Codec codec2 = new T8001Protocol.Codec(codec.getBytes());
        System.out.println(codec2.getValue().getIsuId().getValue());

    }



    public static class T8001Protocol extends PrincipalT905Protocol<T8001Protocol.Data.Codec>{

        public T8001Protocol(String isuId, int messageSerialNo, Data.Codec codec) {
            super(0x8001, isuId, messageSerialNo, codec);
        }

        public T8001Protocol(byte[] bytes) {
            super(bytes);
        }

        public T8001Protocol(String hexString) {
            super(hexString);
        }

        public static class Codec extends ProtocolCodec<T8001Protocol>{
            public Codec(byte[] bytes) {
                super(bytes, T8001Protocol.class);
            }

            public Codec(String hexString) {
                super(hexString, T8001Protocol.class);
            }

            public Codec(T8001Protocol t8001Protocol) {
                super(t8001Protocol, T8001Protocol.class);
            }
        }


        public static class Data extends ProtocolFragment{
            public Data(byte[] bytes) {
                super(bytes);
            }

            public Data(String hexString) {
                super(hexString);
            }

            public Data(int responseMessageSerialNo, int responseMessageId, int result) {
                this.setResponseMessageSerialNo(new UInt16ObjectCodec(responseMessageSerialNo));
                this.setResponseMessageId(new UInt16ObjectCodec(responseMessageId));
                this.setResult(new UInt8ObjectCodec(result));
            }

            @Protocol(order = 0, length = 2)
            UInt16ObjectCodec responseMessageSerialNo;
            @Protocol(order = 1, length = 2)
            UInt16ObjectCodec responseMessageId;
            @Protocol(order = 2, length = 1)
            UInt8ObjectCodec result;

            public UInt16ObjectCodec getResponseMessageSerialNo() {
                return responseMessageSerialNo;
            }

            public void setResponseMessageSerialNo(UInt16ObjectCodec responseMessageSerialNo) {
                this.responseMessageSerialNo = responseMessageSerialNo;
            }

            public UInt16ObjectCodec getResponseMessageId() {
                return responseMessageId;
            }

            public void setResponseMessageId(UInt16ObjectCodec responseMessageId) {
                this.responseMessageId = responseMessageId;
            }

            public UInt8ObjectCodec getResult() {
                return result;
            }

            public void setResult(UInt8ObjectCodec result) {
                this.result = result;
            }

            public static class Codec extends ProtocolCodec<Data>{
                public Codec(byte[] bytes) {
                    super(bytes, Data.class);
                }

                public Codec(String hexString) {
                    super(hexString, Data.class);
                }

                public Codec(Data data) {
                    super(data, Data.class);
                }
            }
        }
    }
}
