package io.github.justinalucard.protocolcodec.protocols.t905.codecs;

import io.github.justinalucard.protocolcodec.naives.AsciiStringObjectCodec;

import java.util.Arrays;

/**
 * 一般针对协议字段设置为padding.left或者padding.right的情况，0x00字符串解析时，会被解析为乱码，反序列化时，将忽略所有头尾为0x00的字节
 */
public class AsciiTrim0x00ObjectCodec extends AsciiStringObjectCodec {

    public AsciiTrim0x00ObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public AsciiTrim0x00ObjectCodec(String value) {
        this(value, false);
    }

    public AsciiTrim0x00ObjectCodec(String s, boolean hexString) {
        super(s, hexString);
    }

    @Override
    protected String deserialize() {

        int startIndex = 0;
        int endIndex = 0;

        if(this.getBytes().length > 0) {
            for (int i = 0; i < this.getBytes().length; i++) {
                if((this.getBytes()[i] & 0xFF) != 0x00){
                    startIndex = i;
                    break;
                }
            }

            for (int i = this.getBytes().length - 1; i >= 0; i--) {
                if((this.getBytes()[i] & 0xFF) != 0x00){
                    endIndex = i;
                    break;
                }
            }

            byte[] newBytes = Arrays.copyOfRange(this.getBytes(), startIndex, endIndex + 1);
            return new String(newBytes, getCharset());
        }
        return "";
    }

}
