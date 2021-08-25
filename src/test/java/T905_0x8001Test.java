import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.AutoMapperProtocolData;
import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt8ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import org.junit.Test;


public class T905_0x8001Test {

    @Test
    public void testT8001() {
        byte[] bytes = new byte[2];
        bytes[0] = 1;
        bytes[1] = 2;
        System.out.println(new UInt16ObjectCodec(bytes).getValue());
        new UInt16ObjectCodec(3);


        T8001Protocol.Model model = new T8001Protocol.Model();
        model.setResponseMessageId(1);
        model.setResult(2);
        model.setResponseMessageSerialNo(3);

//        T8001Protocol.Data data = new T8001Protocol.Data(model);
//        T8001Protocol.Data.Codec dataCodec = new T8001Protocol.Data.Codec(data);
//        T8001Protocol t8001Protocol = new T8001Protocol("101234567890", 100, dataCodec);
//        T8001Protocol.Codec codec = new T8001Protocol.Codec(t8001Protocol);

        T8001Protocol.Codec codec = T8001Protocol.create("101234567890", 100, model);
        System.out.println(codec);
        System.out.println(codec.getValue().getIsuId().getValue());

        byte[] bytes1 = new byte[]{126, -128, 1, 0, 13, 16, 18, 52, 86, 120, -112, 0, 100, 0, 1, 0, 2, 3, 30, 126};

        T8001Protocol.Codec codec2 = new T8001Protocol.Codec(codec.getBytes());
        System.out.println(codec2.getValue().getIsuId().getValue());



        System.out.println(codec2.getValue().getData().getValue().get().getResponseMessageId());
        System.out.println(codec2.getValue().getData().getValue().get().getResult());
        System.out.println(codec2.getValue().getData().getValue().get().getResponseMessageSerialNo());

    }


    public static class T8001Protocol extends PrincipalT905Protocol<T8001Protocol.Data.Codec> {

        public T8001Protocol(String isuId, int messageSerialNo, Data.Codec codec) {
            super(0x8001, isuId, messageSerialNo, codec);
        }

        public T8001Protocol(byte[] bytes) {
            super(bytes);
        }

        public T8001Protocol(String hexString) {
            super(hexString);
        }

        public static T8001Protocol.Codec  create(String isuId, int messageSerialNo, Model model){
            T8001Protocol.Data data = new T8001Protocol.Data(model);
            T8001Protocol.Data.Codec dataCodec = new T8001Protocol.Data.Codec(data);
            T8001Protocol t8001Protocol = new T8001Protocol(isuId, messageSerialNo, dataCodec);
            return new Codec(t8001Protocol);
        }

        public static class Codec extends PrincipalT905ProtocolCodec<T8001Protocol> {
            public Codec(byte[] bytes) {
                super(bytes);
            }

            public Codec(String hexString) {
                super(hexString);
            }

            public Codec(T8001Protocol t8001Protocol) {
                super(t8001Protocol);
            }
        }


        public static class Data extends AutoMapperProtocolData<Model> {

            public Data(Model model){
                super(model);
            }

            public Data(byte[] bytes) {
                super(bytes);
            }

            public Data(String hexString) {
                super(hexString);
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

            public UInt16ObjectCodec getResponseMessageId() {
                return responseMessageId;
            }

            public UInt8ObjectCodec getResult() {
                return result;
            }

            public static class Codec extends ProtocolCodec<Data> {

                public Codec(byte[] bytes) {
                    super(bytes);
                }

                public Codec(String hexString) {
                    super(hexString);
                }

                public Codec(Data data) {
                    super(data);
                }
            }
        }

        public static class Model {
            Integer responseMessageSerialNo;
            Integer responseMessageId;
            Integer result;

            public Integer getResponseMessageSerialNo() {
                return responseMessageSerialNo;
            }

            public void setResponseMessageSerialNo(Integer responseMessageSerialNo) {
                this.responseMessageSerialNo = responseMessageSerialNo;
            }

            public Integer getResponseMessageId() {
                return responseMessageId;
            }

            public void setResponseMessageId(Integer responseMessageId) {
                this.responseMessageId = responseMessageId;
            }

            public Integer getResult() {
                return result;
            }

            public void setResult(Integer result) {
                this.result = result;
            }
        }
    }
}
