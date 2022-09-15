package com.october.to.finish.restaurantwebapp.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceExceptionTest {
    private final Exception e = new Exception();

    @Test
    void simpleTest() {
        Exception serviceException = new ServiceException();
        assertNotNull(serviceException);
        String message = "message";
        Exception svcException = new ServiceException(message);
        assertEquals(message, svcException.getMessage());
        Exception svcExc = new ServiceException(message, e);
        assertEquals(message, svcExc.getMessage());
        assertEquals(e, svcExc.getCause());
    }
}