import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.AutoMapperProtocolData;
import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import org.junit.Test;


public class BranchProtocolTest {

    @Test
    public void testTBranch() {
        byte[] bytes = new byte[2];
        bytes[0] = 1;
        bytes[1] = 2;
        new UInt16ObjectCodec(bytes);

        TBranchProtocol.Model model = new TBranchProtocol.Model();
        model.setType(2);
        model.setAlwaysValue1(2);
        model.setType1Value(3);
        model.setType2Value(4);
        model.setAlwaysValue2(5);



        TBranchProtocol.Codec codec = TBranchProtocol.create("101234567890", 0xFFFF, model);
        System.out.println(codec);
        System.out.println(codec.getValue().getIsuId().getValue());


        TBranchProtocol.Codec codec2 = new TBranchProtocol.Codec(codec.getBytes());
        System.out.println(codec2.getValue().getIsuId().getValue());



        System.out.println(codec2.getValue().getData().getValue().get().getType());
        System.out.println(codec2.getValue().getData().getValue().get().getAlwaysValue1());
        System.out.println(codec2.getValue().getData().getValue().get().getType1Value());
        System.out.println(codec2.getValue().getData().getValue().get().getType2Value());
        System.out.println(codec2.getValue().getData().getValue().get().getAlwaysValue2());

    }


    public static class TBranchProtocol extends PrincipalT905Protocol<TBranchProtocol.Data.Codec> {

        public TBranchProtocol(String isuId, int messageSerialNo, Data.Codec codec) {
            super(0x9999, isuId, messageSerialNo, codec);
        }

        public TBranchProtocol(byte[] bytes) {
            super(bytes);
        }

        public TBranchProtocol(String hexString) {
            super(hexString);
        }

        public static Codec  create(String isuId, int messageSerialNo, Model model){
            Data data = new Data(model);
            Data.Codec dataCodec = new Data.Codec(data);
            TBranchProtocol tBranchProtocol = new TBranchProtocol(isuId, messageSerialNo, dataCodec);
            return new Codec(tBranchProtocol);
        }

        public static class Codec extends PrincipalT905ProtocolCodec<TBranchProtocol> {
            public Codec(byte[] bytes) {
                super(bytes);
            }

            public Codec(String hexString) {
                super(hexString);
            }

            public Codec(TBranchProtocol tBranchProtocol) {
                super(tBranchProtocol, TBranchProtocol.class);
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

            @Protocol(order = 0, length = 2, isBranchRoot = true)
            UInt16ObjectCodec type;
            @Protocol(order = 1, length = 2)
            UInt16ObjectCodec alwaysValue1;
            @Protocol(order = 2, length = 2, isBranchNode = true, branchNo = "0001")
            UInt16ObjectCodec type1Value;
            @Protocol(order = 3, length = 2, isBranchNode = true, branchNo = "0002")
            UInt16ObjectCodec type2Value;
            @Protocol(order = 4, length = 2)
            UInt16ObjectCodec alwaysValue2;

            public UInt16ObjectCodec getType() {
                return type;
            }

            public UInt16ObjectCodec getAlwaysValue1() {
                return alwaysValue1;
            }

            public UInt16ObjectCodec getType1Value() {
                return type1Value;
            }

            public UInt16ObjectCodec getType2Value() {
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
            Integer type;
            Integer alwaysValue1;
            Integer type1Value;
            Integer type2Value;
            Integer alwaysValue2;

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public Integer getAlwaysValue1() {
                return alwaysValue1;
            }

            public void setAlwaysValue1(Integer alwaysValue1) {
                this.alwaysValue1 = alwaysValue1;
            }

            public Integer getType1Value() {
                return type1Value;
            }

            public void setType1Value(Integer type1Value) {
                this.type1Value = type1Value;
            }

            public Integer getType2Value() {
                return type2Value;
            }

            public void setType2Value(Integer type2Value) {
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
