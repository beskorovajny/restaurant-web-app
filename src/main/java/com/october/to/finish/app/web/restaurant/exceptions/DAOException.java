package com.october.to.finish.app.web.restaurant.exceptions;

public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }
}
