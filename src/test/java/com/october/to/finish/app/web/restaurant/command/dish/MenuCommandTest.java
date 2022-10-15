package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private DishService dishService;
    @InjectMocks
    private MenuCommand menuCommand;

    @Test
    void executeTest() throws CommandException {
        assertEquals("menu.jsp", menuCommand.execute(request, response));
    }

    @Test
    void shouldNotExecuteTest() throws ServiceException {
        when(dishService.findAll(anyInt())).thenThrow(ServiceException.class);
        assertThrows(CommandException.class, ()-> menuCommand.execute(request, response));
    }
}