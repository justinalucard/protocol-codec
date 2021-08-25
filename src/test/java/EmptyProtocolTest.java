import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.AutoMapperProtocolData;
import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.naives.EmptyObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import org.junit.Test;


public class EmptyProtocolTest {

    @Test
    public void testTEmpty() {

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
