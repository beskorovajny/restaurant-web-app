package com.october.to.finish.app.web.restaurant.command;

import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class LanguageCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @Test
    void executeTest() throws FatalApplicationException, CommandException {
        final AppCommand command = new LanguageCommand();
        final String locale = "locale";
        final String page = "test_page";
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(locale)).thenReturn(locale);
        when(request.getParameter("pageToProcess")).thenReturn(page);
        assertEquals("controller?command=test_page", command.execute(request, response));
    }
}