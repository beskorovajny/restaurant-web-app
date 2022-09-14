package com.october.to.finish.restaurantwebapp.exceptions;

public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Exception exception) {
        super(message, exception);
    }
}
