package com.october.to.finish.restaurantwebapp.exceptions;

public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }

}
