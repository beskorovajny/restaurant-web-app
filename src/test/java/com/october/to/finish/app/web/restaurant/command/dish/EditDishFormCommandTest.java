package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
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
class EditDishFormCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private DishService dishService;
    @InjectMocks
    private EditDishFormCommand targetCommand;

    @Test
    void executeTest() throws FatalApplicationException, CommandException {
        when(request.getParameter(anyString())).thenReturn("1");
        assertEquals("edit_dish.jsp", targetCommand.execute(request, response));
    }
    @Test
    void shouldNotExecuteTest() throws ServiceException {
        when(request.getParameter(anyString())).thenReturn("1");
        when(dishService.findById(1)).thenThrow(ServiceException.class);
        assertThrows(CommandException.class, () -> targetCommand.execute(request, response));
    }
}