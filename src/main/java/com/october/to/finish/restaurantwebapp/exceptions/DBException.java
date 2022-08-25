package com.october.to.finish.restaurantwebapp.exceptions;

public class DBException extends Exception {

    public DBException() {}

    public DBException(String message) {
        super(message);
    }
    public DBException(String message, Exception exception) {
        super(message, exception);
    }
}
