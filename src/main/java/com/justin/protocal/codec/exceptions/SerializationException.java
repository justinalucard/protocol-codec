package com.justin.protocal.codec.exceptions;

/**
 * 所有协议解析过程的通用异常
 */
public class SerializationException extends RuntimeException {

    public SerializationException() {
        super();
    }

    public SerializationException(String message) {
        super(message);
    }


    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }


    public SerializationException(Throwable cause) {
        super(cause);
    }

    protected SerializationException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
