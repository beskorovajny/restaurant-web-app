package com.october.to.finish.restaurantwebapp.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FatalApplicationExceptionTest {
    private final Exception e = new Exception();

    @Test
    void simpleTest() {
        Exception fatalException = new FatalApplicationException();
        assertNotNull(fatalException);
        String message = "message";
        Exception fatalErrException = new FatalApplicationException(message);
        assertEquals(message, fatalErrException.getMessage());
        Exception errExc = new FatalApplicationException(message, e);
        assertEquals(message, errExc.getMessage());
        assertEquals(e, errExc.getCause());
    }
}