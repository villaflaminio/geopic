package com.flaminiovilla.geris.exception;

public class UtilsException extends RuntimeException {
    public enum utilsExceptionCode{
       DATA_WRONG_FORMAT
    }
    public UtilsException(UtilsException.utilsExceptionCode message) {
        super(message.toString());
    }
}
