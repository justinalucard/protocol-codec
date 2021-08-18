package io.github.justinalucard.protocalcodec.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConverterUtils {

    public static int getInt(byte[] bytes) {
        return getInt(bytes, 0);
    }

    public static boolean getBoolean(byte[] b) {
        return getBoolean(b, 0);
    }

    public static char getChar(byte[] b) {
        return getChar(b, 0);
    }

    public static short getShort(byte[] b) {
        return getShort(b, 0);
    }


    public static float getFloat(byte[] b) {
        return getFloat(b, 0);
    }

    public static long getLong(byte[] b) {
        return getLong(b, 0);
    }

    public static double getDouble(byte[] b) {
        return getDouble(b, 0);
    }

    public static String getString(byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }


    public static String getString(byte[] b, Charset charset) {
        return new String(b, charset);
    }


    public static boolean getBoolean(byte[] b, int off) {
        return b[off] != 0;
    }

    public static int getUInt16(byte[] b){
        return getUInt16(b, 0);
    }

    public static long getUInt32(byte[] b){
        return getUInt32(b, 0);
    }

    public static int getUInt8(byte[] b){
        return getUInt8(b, 0);
    }

    public static char getChar(byte[] b, int off) {
        return (char) ((b[off + 1] & 0xFF) +
                (b[off] << 8));
    }

    public static short getShort(byte[] b, int off) {
        return (short) ((b[off + 1] & 0xFF) +
                (b[off] << 8));
    }

    public static int getUInt8(byte[] b, int off) {
        byte[] b2 = new byte[4];
        System.arraycopy(b, off, b2, 3, 1);
        return getInt(b2);
    }

    public static int getUInt16(byte[] b, int off) {
        byte[] b2 = new byte[4];
        System.arraycopy(b, off, b2, 2, 2);
        return getInt(b2);
    }

    public static long getUInt32(byte[] b, int off) {
        byte[] b2 = new byte[8];
        System.arraycopy(b, off, b2, 4, 4);
        return getLong(b2);
    }

    public static int getInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF)) +
                ((b[off + 2] & 0xFF) << 8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off]) << 24);
    }

    public static float getFloat(byte[] b, int off) {
        return Float.intBitsToFloat(getInt(b, off));
    }

    public static long getLong(byte[] b, int off) {
        return ((b[off + 7] & 0xFFL)) +
                ((b[off + 6] & 0xFFL) << 8) +
                ((b[off + 5] & 0xFFL) << 16) +
                ((b[off + 4] & 0xFFL) << 24) +
                ((b[off + 3] & 0xFFL) << 32) +
                ((b[off + 2] & 0xFFL) << 40) +
                ((b[off + 1] & 0xFFL) << 48) +
                (((long) b[off]) << 56);
    }

    public static double getDouble(byte[] b, int off) {
        return Double.longBitsToDouble(getLong(b, off));
    }


    /**
     * 以字节数组的形式返回指定的布尔值
     *
     * @param data 一个布尔值
     * @return 长度为 1 的字节数组
     */

    public static byte[] getBytes(boolean data) {
        byte[] bytes = new byte[1];

        bytes[0] = (byte) (data ? 1 : 0);

        return bytes;

    }

    /**
     * 以字节数组的形式返回指定的 16 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 2 的字节数组
     */

    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];

        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);

            bytes[1] = (byte) ((data & 0xff00) >> 8);

        } else {
            bytes[1] = (byte) (data & 0xff);

            bytes[0] = (byte) ((data & 0xff00) >> 8);

        }

        return bytes;

    }

    /**
     * 以字节数组的形式返回指定的 Unicode 字符值
     *
     * @param data 要转换的字符
     * @return 长度为 2 的字节数组
     */

    public static byte[] getBytes(char data) {
        byte[] bytes = new byte[2];

        if (isLittleEndian()) {
            bytes[0] = (byte) (data);

            bytes[1] = (byte) (data >> 8);

        } else {
            bytes[1] = (byte) (data);

            bytes[0] = (byte) (data >> 8);

        }

        return bytes;

    }

    /**
     * 以字节数组的形式返回指定的 32 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 4 的字节数组
     */

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];

        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);

            bytes[1] = (byte) ((data & 0xff00) >> 8);

            bytes[2] = (byte) ((data & 0xff0000) >> 16);

            bytes[3] = (byte) ((data & 0xff000000) >> 24);

        } else {
            bytes[3] = (byte) (data & 0xff);

            bytes[2] = (byte) ((data & 0xff00) >> 8);

            bytes[1] = (byte) ((data & 0xff0000) >> 16);

            bytes[0] = (byte) ((data & 0xff000000) >> 24);

        }

        return bytes;

    }

    /**
     * 以字节数组的形式返回指定的 64 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 8 的字节数组
     */

    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];

        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);

            bytes[1] = (byte) ((data >> 8) & 0xff);

            bytes[2] = (byte) ((data >> 16) & 0xff);

            bytes[3] = (byte) ((data >> 24) & 0xff);

            bytes[4] = (byte) ((data >> 32) & 0xff);

            bytes[5] = (byte) ((data >> 40) & 0xff);

            bytes[6] = (byte) ((data >> 48) & 0xff);

            bytes[7] = (byte) ((data >> 56) & 0xff);

        } else {
            bytes[7] = (byte) (data & 0xff);

            bytes[6] = (byte) ((data >> 8) & 0xff);

            bytes[5] = (byte) ((data >> 16) & 0xff);

            bytes[4] = (byte) ((data >> 24) & 0xff);

            bytes[3] = (byte) ((data >> 32) & 0xff);

            bytes[2] = (byte) ((data >> 40) & 0xff);

            bytes[1] = (byte) ((data >> 48) & 0xff);

            bytes[0] = (byte) ((data >> 56) & 0xff);

        }

        return bytes;

    }

    /**
     * 以字节数组的形式返回指定的单精度浮点值
     *
     * @param data 要转换的数字
     * @return 长度为 4 的字节数组
     */

    public static byte[] getBytes(float data) {
        return getBytes(Float.floatToIntBits(data));

    }

    /**
     * 以字节数组的形式返回指定的双精度浮点值
     *
     * @param data 要转换的数字
     * @return 长度为 8 的字节数组
     */

    public static byte[] getBytes(double data) {
        return getBytes(Double.doubleToLongBits(data));

    }

    /**
     * 将指定字符串中的所有字符编码为一个字节序列
     *
     * @param data 包含要编码的字符的字符串
     * @return 一个字节数组，包含对指定的字符集进行编码的结果
     */

    public static byte[] getBytes(String data) {
        return data.getBytes(StandardCharsets.UTF_8);

    }

    /**
     * 将指定字符串中的所有字符编码为一个字节序列
     *
     * @param data        包含要编码的字符的字符串
     * @param charset 字符集编码
     * @return 一个字节数组，包含对指定的字符集进行编码的结果
     */
    public static byte[] getBytes(String data, Charset charset) {
        return data.getBytes(charset);

    }

    private static boolean isLittleEndian() {
        return false;
//        return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    }
}
