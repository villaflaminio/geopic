package com.flaminiovilla.geopic.security.exception;

public class UserException extends RuntimeException{

    public enum userExceptionCode{
        PARAMETER_NULL,
        EMAIL_NOT_EXIST,
        USER_ALREADY_EXISTS,
        AUTHORITY_NOT_EXIST,
        USER_NOT_LOGGED_IN,
        AUTHORITY_UNREACHABALE
    }
    public UserException(userExceptionCode message) {
        super(message.toString());
    }

}
