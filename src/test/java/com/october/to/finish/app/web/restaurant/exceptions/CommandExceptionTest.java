package com.october.to.finish.app.web.restaurant.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandExceptionTest {
    private final Exception e = new Exception();

    @Test
    void simpleTest() {
        Exception commandException = new CommandException();
        assertNotNull(commandException);
        String message = "message";
        Exception commandExc = new CommandException(message);
        assertEquals(message, commandExc.getMessage());
        Exception commandEx = new CommandException(message, e);
        assertEquals(message, commandEx.getMessage());
        assertEquals(e, commandEx.getCause());
    }

}