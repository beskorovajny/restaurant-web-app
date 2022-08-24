package com.october.to.finish.restaurantwebapp.exceptions;

public class DBException extends Throwable {
    public DBException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
