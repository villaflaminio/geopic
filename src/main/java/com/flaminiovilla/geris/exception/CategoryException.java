package com.flaminiovilla.geris.exception;


public class CategoryException extends RuntimeException{
    public enum CategoryExceptionCode{
        CATEGORY_NOT_FOUND,
        CATEGORY_ALREADY_EXISTS,
        CATEGORY_DELETE_ERROR,
        CATEGORY_ID_NOT_EXIST
    }

    public CategoryException(CategoryExceptionCode message) {
        super(message.toString());
    }
}
