package com.october.to.finish.app.web.restaurant.command;

import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
@ExtendWith(MockitoExtension.class)
class ExceptionCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Test
    void executeTest() throws FatalApplicationException, CommandException {
        final AppCommand testCommand = new ExceptionCommand();
        assertEquals("error.jsp", testCommand.execute(request, response));
    }
}