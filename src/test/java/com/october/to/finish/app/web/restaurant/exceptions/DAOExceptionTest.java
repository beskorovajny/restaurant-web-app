package com.october.to.finish.app.web.restaurant.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOExceptionTest {
    private final Exception e = new Exception();

    @Test
    void simpleTest() {
        Exception daoException = new DAOException();
        assertNotNull(daoException);
        String message = "message";
        Exception daoExc = new DAOException(message);
        assertEquals(message, daoExc.getMessage());
        Exception daoEx = new DAOException(message, e);
        assertEquals(message, daoEx.getMessage());
        assertEquals(e, daoEx.getCause());
    }
}