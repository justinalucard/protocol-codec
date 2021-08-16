# protocol-codec
## 协议编解码器
在tcp、串口等消息传输中，通常我们会使用有规则的字节流进行数据交互，例如：
起始符|消息头|***长度***|消息体|结束符
--|:--:|:--:|:--:|---:

在java中，我们一般使用netty之类的框架处理tcp数据的收发，接收和发送的数据格式皆为byte[]。在接收过程中，我们需要按照事先设定的格式，进行字节的逐一读取，并进行相应的类型转换，才可还原成可用的pojo对象。发送时，我们则需要按照规则，进行pojo到byte[]的转换，这种过程我们一般称作为序列化（编码）与反序列化（解码）。

protocol-codec就是为了解决这些繁复工作的通用实现，里面内置了一些简单类型的编解码器，也可以自行扩展增加新的编解码器。在需要编解码时，只需实现具体编码器和数据类型，即可实现对应的转换。

> 适用范围：串口通讯协议、tcp协议，例如：JT/T905 JT/T808等通讯协议。

## 示例代码
下面演示了JT/T905协议中的0x8001协议的具体实现方式

### 0x8001的协议定义:
```
//PrincipalT905Protocol这个类已经在protocol-codec源码里内置
public class T8001Protocol extends PrincipalT905Protocol<T8001Protocol.Data.Codec>{

    public T8001Protocol(String isuId, int messageSerialNo, Data.Codec codec) {
        super(0x8001, isuId, messageSerialNo, codec);
    }

    public T8001Protocol(byte[] bytes) {
        super(bytes);
    }

    public T8001Protocol(String hexString) {
        super(hexString);
    }

    public static class Codec extends PrincipalT905ProtocolCodec<T8001Protocol> {
        public Codec(byte[] bytes) {
            super(bytes, T8001Protocol.class);
        }

        public Codec(String hexString) {
            super(hexString, T8001Protocol.class);
        }

        public Codec(T8001Protocol t8001Protocol) {
            super(t8001Protocol, T8001Protocol.class);
        }
    }


    public static class Data extends ProtocolFragment{
        public Data(byte[] bytes) {
            super(bytes);
        }

        public Data(String hexString) {
            super(hexString);
        }

        public Data(int responseMessageSerialNo, int responseMessageId, int result) {
            this.responseMessageSerialNo = new UInt16ObjectCodec(responseMessageSerialNo);
            this.responseMessageId = new UInt16ObjectCodec(responseMessageId);
            this.result = new UInt8ObjectCodec(result);
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

        public static class Codec extends ProtocolCodec<Data>{

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
}
    
```

### 0x8001的编码

```
T8001Protocol.Data data = new T8001Protocol.Data(1, 2, 3);
T8001Protocol.Data.Codec dataCodec = new T8001Protocol.Data.Codec(data);
T8001Protocol t8001Protocol = new T8001Protocol("101234567890", 100, dataCodec);
T8001Protocol.Codec codec = new T8001Protocol.Codec(t8001Protocol);
System.out.println(codec); //ProtocolFragment{bytes=[126, -128, 1, 0, 13, 16, 18, 52, 86, 120, -112, 0, 100, 0, 1, 0, 2, 3, 30, 126],hexString='7e8001000d101234567890006400010002031e7e'}
```

### 0x8001的解码

```
T8001Protocol.Codec codec2 = new T8001Protocol.Codec("7e8001000d101234567890006400010002031e7e"); //使用hexString(十六进制字符串)的方式进行解码
byte[] bytes1 = new byte[]{126, -128, 1, 0, 13, 16, 18, 52, 86, 120, -112, 0, 100, 0, 1, 0, 2, 3, 30, 126}; //一般取自于netty或者串口通讯接收的结果
T8001Protocol.Codec codec3 = new T8001Protocol.Codec(bytes1);
System.out.println(codec2.getValue().getIsuId().getValue()); //101234567890
System.out.println(codec3.getValue().getIsuId().getValue()); //101234567890
```