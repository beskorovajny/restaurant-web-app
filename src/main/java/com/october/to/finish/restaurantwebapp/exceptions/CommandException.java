package com.october.to.finish.restaurantwebapp.exceptions;

public class CommandException extends Exception {
    public CommandException() {}
    public CommandException(String message) {
        super(message);
    }
    public CommandException(String message, Throwable exception) {
        super(message, exception);
    }
}
