import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.naives.BitsObjectCodec;
import io.github.justinalucard.protocolcodec.naives.EmptyObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import io.github.justinalucard.protocolcodec.utils.ConverterUtils;
import org.junit.Test;


public class EmptyProtocolTest {

    @Test
    public void testTEmpty() {

        BitsObjectCodec bits = new BitsObjectCodec(new ProtocolFragment("AA").getBytes());
        BitsObjectCodec bits2 = new BitsObjectCodec("101010101");
        System.out.println(bits2.getValue());
        System.out.println(ConverterUtils.getUInt16(bits2.getBytes()));
        BitsObjectCodec bits3 = new BitsObjectCodec(bits2.getBytes());
        System.out.println(bits3.getValue());

        TEmptyProtocol.Codec codec = TEmptyProtocol.create("101234567890", 0xFFFF);
        System.out.println(codec);
        System.out.println(codec.getValue().getIsuId().getValue());


        TEmptyProtocol.Codec codec2 = new TEmptyProtocol.Codec(codec.getBytes());
        System.out.println(codec2.getValue().getIsuId().getValue());

    }


    public static class TEmptyProtocol extends PrincipalT905Protocol<EmptyObjectCodec> {

        public TEmptyProtocol(String isuId, int messageSerialNo, EmptyObjectCodec codec) {
            super(0x9999, isuId, messageSerialNo, codec);
        }

        public TEmptyProtocol(byte[] bytes) {
            super(bytes);
        }

        public TEmptyProtocol(String hexString) {
            super(hexString);
        }

        public static Codec  create(String isuId, int messageSerialNo){
            TEmptyProtocol tEmptyProtocol = new TEmptyProtocol(isuId, messageSerialNo, new EmptyObjectCodec());
            return new Codec(tEmptyProtocol);
        }

        public static class Codec extends PrincipalT905ProtocolCodec<TEmptyProtocol> {
            public Codec(byte[] bytes) {
                super(bytes);
            }

            public Codec(String hexString) {
                super(hexString);
            }

            public Codec(TEmptyProtocol tEmptyProtocol) {
                super(tEmptyProtocol, TEmptyProtocol.class);
            }
        }



    }
}
