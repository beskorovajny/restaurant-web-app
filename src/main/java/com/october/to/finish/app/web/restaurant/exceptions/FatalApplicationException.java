package com.october.to.finish.app.web.restaurant.exceptions;

public class FatalApplicationException extends Exception {
    public FatalApplicationException() {
    }

    public FatalApplicationException(String message) {
        super(message);
    }

    public FatalApplicationException(String message, Throwable exception) {
        super(message, exception);
    }
}
