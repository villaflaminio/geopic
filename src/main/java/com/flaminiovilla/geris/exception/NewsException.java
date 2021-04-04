package com.flaminiovilla.geris.exception;

public class NewsException extends RuntimeException{

    public enum newsExceptionCode{
        NEWS_DELETE_ERROR,
        NEWS_ID_NOT_EXIST,
        CREATING_EXCEPTION
    }

    public NewsException(NewsException.newsExceptionCode message) {
        super(message.toString());
    }
}
