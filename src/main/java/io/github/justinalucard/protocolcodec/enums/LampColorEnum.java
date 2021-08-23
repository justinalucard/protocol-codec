package io.github.justinalucard.protocolcodec.enums;

/**
 * 变灯颜色的枚举
 */
public enum LampColorEnum {
    GREEN(0x01, "绿色"),
    RED(0x02, "红色"),
    BLUE(0x03, "蓝色"),
    YELLOW(0x04, "黄色"),
    RAINBOW(0x05, "彩色");

    int code;
    String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    LampColorEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
