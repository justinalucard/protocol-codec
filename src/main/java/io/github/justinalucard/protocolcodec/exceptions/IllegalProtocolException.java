package io.github.justinalucard.protocolcodec.exceptions;

/**
 * 所有协议解析过程的通用异常
 */
public class IllegalProtocolException extends RuntimeException {

    public IllegalProtocolException() {
        super();
    }

    public IllegalProtocolException(String message) {
        super(message);
    }


    public IllegalProtocolException(String message, Throwable cause) {
        super(message, cause);
    }


    public IllegalProtocolException(Throwable cause) {
        super(cause);
    }

    protected IllegalProtocolException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
