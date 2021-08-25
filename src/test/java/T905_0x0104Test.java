import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.AutoMapperProtocolData;
import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.naives.*;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.codecs.base.GbkString0x00ObjectCodec;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class T905_0x0104Test {

    @Test
    public void testT0104() {
        byte[] bytes = new byte[2];
        bytes[0] = 1;
        bytes[1] = 2;
        new UInt16ObjectCodec(bytes);

        T0104Protocol.Model model = new T0104Protocol.Model();
        model.setResponseMessageSerialNo(100);
        HashMap<UInt16ObjectCodec, ByteArrayObjectCodec> map = new HashMap<>();
        map.put(new UInt16ObjectCodec(0x0010), new ByteArrayObjectCodec(new GbkString0x00ObjectCodec("www.baidu.com").getBytes()));
        map.put(new UInt16ObjectCodec(0x0019), new ByteArrayObjectCodec(new UInt32ObjectCodec(9000L).getBytes()));
        model.setConfigs(map);



        T0104Protocol.Codec codec = T0104Protocol.create("101234567890", 100, model);
        System.out.println(codec);
        System.out.println(codec.getValue().getIsuId().getValue());

        byte[] bytes1 = new byte[]{126, 1, 4, 0, 34, 16, 18, 52, 86, 120, -112, 0, 100, 0, 100, 0, 25, 4, 0, 0, 35, 40, 0, 16, 14, 119, 119, 119, 46, 98, 97, 105, 100, 117, 46, 99, 111, 109, 0, 7, 126};

        T0104Protocol.Codec codec2 = new T0104Protocol.Codec(codec.getBytes());
        System.out.println(codec2.getValue().getIsuId().getValue());



//        System.out.println(codec2.getValue().getData().getValue().get().getConfigs().keySet());

        System.out.println(codec2.getValue().getData().getValue().get().getResponseMessageSerialNo());

    }


    public static class T0104Protocol extends PrincipalT905Protocol<T0104Protocol.Data.Codec> {

        public T0104Protocol(String isuId, int messageSerialNo, Data.Codec codec) {
            super(0x0104, isuId, messageSerialNo, codec);
        }

        public T0104Protocol(byte[] bytes) {
            super(bytes);
        }

        public T0104Protocol(String hexString) {
            super(hexString);
        }

        public static Codec  create(String isuId, int messageSerialNo, Model model){
            Data data = new Data(model);
            Data.Codec dataCodec = new Data.Codec(data);
            T0104Protocol t0104Protocol = new T0104Protocol(isuId, messageSerialNo, dataCodec);
            return new Codec(t0104Protocol);
        }

        public static class Codec extends PrincipalT905ProtocolCodec<T0104Protocol> {
            public Codec(byte[] bytes) {
                super(bytes, T0104Protocol.class);
            }

            public Codec(String hexString) {
                super(hexString, T0104Protocol.class);
            }

            public Codec(T0104Protocol t0104Protocol) {
                super(t0104Protocol, T0104Protocol.class);
            }
        }

        public static class ConfigCodec extends TlvObjectCodec<UInt16ObjectCodec, UInt8ObjectCodec, ByteArrayObjectCodec>{

            public ConfigCodec(byte[] bytes) {
                super(bytes, 2, 1);
            }

            public ConfigCodec(Map<UInt16ObjectCodec, ByteArrayObjectCodec> value) {
                super(value, 2, 1);
            }

            public ConfigCodec(String hexString) {
                super(hexString, 2, 1);
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
            @Protocol(order = 1, isRestOf = true)
            ConfigCodec configs;

            public UInt16ObjectCodec getResponseMessageSerialNo() {
                return responseMessageSerialNo;
            }

            public ConfigCodec getConfigs() {
                return configs;
            }

            public static class Codec extends ProtocolCodec<Data> {

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

        public static class Model {
            Integer responseMessageSerialNo;
            Map<UInt16ObjectCodec, ByteArrayObjectCodec> configs;

            public Integer getResponseMessageSerialNo() {
                return responseMessageSerialNo;
            }

            public void setResponseMessageSerialNo(Integer responseMessageSerialNo) {
                this.responseMessageSerialNo = responseMessageSerialNo;
            }

            public Map<UInt16ObjectCodec, ByteArrayObjectCodec> getConfigs() {
                return configs;
            }

            public void setConfigs(Map<UInt16ObjectCodec, ByteArrayObjectCodec> configs) {
                this.configs = configs;
            }
        }
    }
}
