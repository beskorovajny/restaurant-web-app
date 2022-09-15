package com.october.to.finish.restaurantwebapp.exceptions;

public class FatalApplicationException extends Exception {
    public FatalApplicationException() {
    }

    public FatalApplicationException(String message) {
        super(message);
    }

    public FatalApplicationException(String message, Exception exception) {
        super(message, exception);
    }
}