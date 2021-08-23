import io.github.justinalucard.protocolcodec.enums.LampColorEnum;
import io.github.justinalucard.protocolcodec.naives.ByteArrayObjectCodec;
import io.github.justinalucard.protocolcodec.naives.TlvObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.llc.lamp.LampColorObjectCodec;
import io.github.justinalucard.protocolcodec.utils.ConverterUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TlvObjectCodecTest {
    @Test
    public void testSerializer(){
        Map<LampColorObjectCodec, ByteArrayObjectCodec> map = new HashMap<>();
        map.put(new LampColorObjectCodec(LampColorEnum.RAINBOW), new ByteArrayObjectCodec("1234"));
        map.put(new LampColorObjectCodec(LampColorEnum.RED), new ByteArrayObjectCodec("ABCEF"));
        LampTlvCodec lampTlvCodec = new LampTlvCodec(map);
        System.out.println(lampTlvCodec.getHexString());

        LampTlvCodec lampTlvCodec2 = new LampTlvCodec(lampTlvCodec.getBytes());
        System.out.println(lampTlvCodec2.getValue());
        for (Map.Entry<LampColorObjectCodec, ByteArrayObjectCodec> one: lampTlvCodec2.getValue().entrySet()){
            System.out.println(one.getKey().getValue());
            System.out.println(Arrays.toString(one.getValue().getValue()));
        }

        byte[] bytes = new byte[4];
        bytes[0] = 127;
        long ret = ConverterUtils.getUInt32(bytes, 0);
    }


    public static class LampTlvCodec extends TlvObjectCodec<LampColorObjectCodec, UInt16ObjectCodec, ByteArrayObjectCodec> {

        public LampTlvCodec(byte[] bytes) {
            super(bytes, 2, 2);
        }

        public LampTlvCodec(Map<LampColorObjectCodec, ByteArrayObjectCodec> value) {
            super(value, 2, 2);
        }

        public LampTlvCodec(String hexString) {
            super(hexString, 2, 2);
        }
    }
}
