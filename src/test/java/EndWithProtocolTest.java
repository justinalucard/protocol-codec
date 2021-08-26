import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.AutoMapperProtocolData;
import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.codecs.GbkString0x00ObjectCodec;
import org.junit.Test;


public class EndWithProtocolTest {

    @Test
    public void testEndWith() {
      

        EndWithProtocol.Model model = new EndWithProtocol.Model();
        model.setType("AAA");
        model.setAlwaysValue1("BBB");
        model.setType1Value("CCC");
        model.setType2Value("DDD");
        model.setAlwaysValue2(5);



        EndWithProtocol.Codec codec = EndWithProtocol.create("101234567890", 0xFFFF, model);
        System.out.println(codec);
        System.out.println(codec.getValue().getIsuId().getValue());


        EndWithProtocol.Codec codec2 = new EndWithProtocol.Codec(codec.getBytes());
        System.out.println(codec2.getValue().getIsuId().getValue());



        System.out.println(codec2.getValue().getData().getValue().get().getType());
        System.out.println(codec2.getValue().getData().getValue().get().getAlwaysValue1());
        System.out.println(codec2.getValue().getData().getValue().get().getType1Value());
        System.out.println(codec2.getValue().getData().getValue().get().getType2Value());
        System.out.println(codec2.getValue().getData().getValue().get().getAlwaysValue2());

    }


    public static class EndWithProtocol extends PrincipalT905Protocol<EndWithProtocol.Data.Codec> {

        public EndWithProtocol(String isuId, int messageSerialNo, Data.Codec codec) {
            super(0x9999, isuId, messageSerialNo, codec);
        }

        public EndWithProtocol(byte[] bytes) {
            super(bytes);
        }

        public EndWithProtocol(String hexString) {
            super(hexString);
        }

        public static Codec  create(String isuId, int messageSerialNo, Model model){
            Data data = new Data(model);
            Data.Codec dataCodec = new Data.Codec(data);
            EndWithProtocol endWithProtocol = new EndWithProtocol(isuId, messageSerialNo, dataCodec);
            return new Codec(endWithProtocol);
        }

        public static class Codec extends PrincipalT905ProtocolCodec<EndWithProtocol> {
            public Codec(byte[] bytes) {
                super(bytes);
            }

            public Codec(String hexString) {
                super(hexString);
            }

            public Codec(EndWithProtocol endWithProtocol) {
                super(endWithProtocol, EndWithProtocol.class);
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

            @Protocol(order = 0, lengthByEndWith = "00")
            GbkString0x00ObjectCodec type;
            @Protocol(order = 1, lengthByEndWith = "00")
            GbkString0x00ObjectCodec alwaysValue1;
            @Protocol(order = 2, lengthByEndWith = "00")
            GbkString0x00ObjectCodec type1Value;
            @Protocol(order = 3, lengthByEndWith = "00")
            GbkString0x00ObjectCodec type2Value;
            @Protocol(order = 4, length = 2)
            UInt16ObjectCodec alwaysValue2;

            public GbkString0x00ObjectCodec getType() {
                return type;
            }

            public GbkString0x00ObjectCodec getAlwaysValue1() {
                return alwaysValue1;
            }

            public GbkString0x00ObjectCodec getType1Value() {
                return type1Value;
            }

            public GbkString0x00ObjectCodec getType2Value() {
                return type2Value;
            }

            public UInt16ObjectCodec getAlwaysValue2() {
                return alwaysValue2;
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
            String type;
            String alwaysValue1;
            String type1Value;
            String type2Value;
            Integer alwaysValue2;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAlwaysValue1() {
                return alwaysValue1;
            }

            public void setAlwaysValue1(String alwaysValue1) {
                this.alwaysValue1 = alwaysValue1;
            }

            public String getType1Value() {
                return type1Value;
            }

            public void setType1Value(String type1Value) {
                this.type1Value = type1Value;
            }

            public String getType2Value() {
                return type2Value;
            }

            public void setType2Value(String type2Value) {
                this.type2Value = type2Value;
            }

            public Integer getAlwaysValue2() {
                return alwaysValue2;
            }

            public void setAlwaysValue2(Integer alwaysValue2) {
                this.alwaysValue2 = alwaysValue2;
            }
        }
    }
}
