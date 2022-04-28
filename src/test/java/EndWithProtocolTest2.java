import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.core.AutoMapperProtocolData;
import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.exceptions.SerializationException;
import io.github.justinalucard.protocolcodec.naives.Bcd8421ObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt16ObjectCodec;
import io.github.justinalucard.protocolcodec.naives.UInt8ObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905Protocol;
import io.github.justinalucard.protocolcodec.protocols.t905.PrincipalT905ProtocolCodec;
import io.github.justinalucard.protocolcodec.protocols.t905.codecs.GbkString0x00ObjectCodec;
import org.junit.Assert;
import org.junit.Test;


public class EndWithProtocolTest2 {

    @Test
    public void testEndWith() {


        EndWithProtocol.Model model = new EndWithProtocol.Model();

        model.setCommand(1);
        model.setDeviceType(1);
        model.setManufacture(2);
        model.setHardwareVersionNo("99");
        model.setSoftwareVersionNo("7788");
        model.setApn("www.baidu.com");
        model.setApnUsername("111");
//        model.setApnPassword("123456");   这里故意把ApnPassword的赋值忽略，然后触发异常，因为模型赋值不允许某个字段为null
        model.setUpgradeServerAddress("www.taobao.com");
        model.setUpgradeServerPort(8080);

        Assert.assertThrows(SerializationException.class, () ->{
            EndWithProtocol.create("101234567890", 0xFFFF, model);
        });

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

            @Protocol(order = 0, length = 1)
            UInt8ObjectCodec command;
            @Protocol(order = 1, length = 1)
            UInt8ObjectCodec deviceType;
            @Protocol(order = 2, length = 1)
            UInt8ObjectCodec manufacture;
            @Protocol(order = 3, length = 1)
            Bcd8421ObjectCodec hardwareVersionNo;
            @Protocol(order = 4, length = 2)
            Bcd8421ObjectCodec softwareVersionNo;
            @Protocol(order = 5, lengthByEndWith = "00")
            GbkString0x00ObjectCodec apn;
            @Protocol(order = 6, lengthByEndWith = "00")
            GbkString0x00ObjectCodec apnUsername;
            @Protocol(order = 7, lengthByEndWith = "00")
            GbkString0x00ObjectCodec apnPassword;
            @Protocol(order = 8, lengthByEndWith = "00")
            GbkString0x00ObjectCodec upgradeServerAddress;
            @Protocol(order = 9, length = 2)
            UInt16ObjectCodec upgradeServerPort;

            public UInt8ObjectCodec getCommand() {
                return command;
            }

            public UInt8ObjectCodec getDeviceType() {
                return deviceType;
            }

            public UInt8ObjectCodec getManufacture() {
                return manufacture;
            }

            public Bcd8421ObjectCodec getHardwareVersionNo() {
                return hardwareVersionNo;
            }

            public Bcd8421ObjectCodec getSoftwareVersionNo() {
                return softwareVersionNo;
            }

            public GbkString0x00ObjectCodec getApn() {
                return apn;
            }

            public GbkString0x00ObjectCodec getApnUsername() {
                return apnUsername;
            }

            public GbkString0x00ObjectCodec getApnPassword() {
                return apnPassword;
            }

            public GbkString0x00ObjectCodec getUpgradeServerAddress() {
                return upgradeServerAddress;
            }

            public UInt16ObjectCodec getUpgradeServerPort() {
                return upgradeServerPort;
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
            Integer command;
            Integer deviceType;
            Integer manufacture;
            String hardwareVersionNo;
            String softwareVersionNo;
            String apn;
            String apnUsername;
            String apnPassword;
            String upgradeServerAddress;
            Integer upgradeServerPort;

            public Integer getCommand() {
                return command;
            }

            public void setCommand(Integer command) {
                this.command = command;
            }

            public Integer getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(Integer deviceType) {
                this.deviceType = deviceType;
            }

            public Integer getManufacture() {
                return manufacture;
            }

            public void setManufacture(Integer manufacture) {
                this.manufacture = manufacture;
            }

            public String getHardwareVersionNo() {
                return hardwareVersionNo;
            }

            public void setHardwareVersionNo(String hardwareVersionNo) {
                this.hardwareVersionNo = hardwareVersionNo;
            }

            public String getSoftwareVersionNo() {
                return softwareVersionNo;
            }

            public void setSoftwareVersionNo(String softwareVersionNo) {
                this.softwareVersionNo = softwareVersionNo;
            }

            public String getApn() {
                return apn;
            }

            public void setApn(String apn) {
                this.apn = apn;
            }

            public String getApnUsername() {
                return apnUsername;
            }

            public void setApnUsername(String apnUsername) {
                this.apnUsername = apnUsername;
            }

            public String getApnPassword() {
                return apnPassword;
            }

            public void setApnPassword(String apnPassword) {
                this.apnPassword = apnPassword;
            }

            public String getUpgradeServerAddress() {
                return upgradeServerAddress;
            }

            public void setUpgradeServerAddress(String upgradeServerAddress) {
                this.upgradeServerAddress = upgradeServerAddress;
            }

            public Integer getUpgradeServerPort() {
                return upgradeServerPort;
            }

            public void setUpgradeServerPort(Integer upgradeServerPort) {
                this.upgradeServerPort = upgradeServerPort;
            }
        }
    }
}
