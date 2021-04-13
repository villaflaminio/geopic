package com.flaminiovilla.geris.exception;

public class SecretaryException extends RuntimeException{
    public enum secretaryExceptionCode{
        SECRETARY_NOT_EXIST,
        SECRETARY_NAME_ALREADY_EXISTING,
        SECRETARY_ID_NOT_EXIST,
        SECRETARY_DELETE_ERROR,
        ACTION_NOT_PERMITTED
    }

    public SecretaryException(SecretaryException.secretaryExceptionCode message) {
        super(message.toString());
    }
}
