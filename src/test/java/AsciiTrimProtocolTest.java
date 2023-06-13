import io.github.justinalucard.protocolcodec.naives.EmptyObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.codecs.AsciiTrim0x00ObjectCodec;
import org.junit.Test;


public class AsciiTrimProtocolTest {

    @Test
    public void textAsciiTrimSerialize() {

//        AsciiTrim0x00ObjectCodec codec = new AsciiTrim0x00ObjectCodec("123");
//
//        System.out.println(codec.getHexString());

        byte[] bytes = new byte[] {0, 0, 0, 36, 35, 0, 0};
        AsciiTrim0x00ObjectCodec codec2 = new AsciiTrim0x00ObjectCodec("00000000414243440000", true);
//        AsciiTrim0x00ObjectCodec codec2 = new AsciiTrim0x00ObjectCodec("0", false);
        System.out.println(codec2.getHexString());
        System.out.println(codec2.getValue().length());
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
