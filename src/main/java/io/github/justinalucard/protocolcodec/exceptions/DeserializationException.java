package io.github.justinalucard.protocolcodec.exceptions;

/**
 * 所有协议反序列化过程的通用异常
 */
public class DeserializationException extends RuntimeException {

    public DeserializationException() {
        super();
    }

    public DeserializationException(String message) {
        super(message);
    }


    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }


    public DeserializationException(Throwable cause) {
        super(cause);
    }

    protected DeserializationException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
