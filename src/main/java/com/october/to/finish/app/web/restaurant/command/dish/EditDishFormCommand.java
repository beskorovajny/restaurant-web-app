package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditDishFormCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(EditDishFormCommand.class);
    private final DishService dishService;
    public EditDishFormCommand(DishService dishService) {
        this.dishService = dishService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        Dish dish = null;
        long id = Long.parseLong(request.getParameter("dishId"));
        try {
            dish = dishService.findById(id);
        } catch (ServiceException e) {
            LOGGER.error("[EditDishFormCommand] Can't receive dish by id:[{}]", id);
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("dish", dish);
        return "edit_dish.jsp";
    }
}
