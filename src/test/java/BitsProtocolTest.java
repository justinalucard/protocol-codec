import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.naives.BitsObjectCodec;
import io.github.justinalucard.protocolcodec.naives.EmptyObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import org.junit.Test;


public class BitsProtocolTest {

    @Test
    public void testTBits() {

        BitsObjectCodec bits = new BitsObjectCodec(new ProtocolFragment("AABB").getBytes());


        System.out.println(bits.getValue());

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
