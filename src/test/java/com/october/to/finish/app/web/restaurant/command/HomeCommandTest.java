package com.october.to.finish.app.web.restaurant.command;

import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HomeCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;


    @Test
    void executeTest() throws CommandException, FatalApplicationException {
        final AppCommand testCommand = new HomeCommand();
        assertEquals("home.jsp", testCommand.execute(request, response));
    }
}