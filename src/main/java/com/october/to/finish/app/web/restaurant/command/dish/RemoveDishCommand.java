package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveDishCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(RemoveDishCommand.class);
    private final DishService dishService;

    public RemoveDishCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            dishService.delete(Integer.parseInt(request.getParameter("dishId")));
            return "controller?command=dishes";
        } catch (ServiceException e) {
            LOGGER.error("[RemoveDishCommand] Dish wasn't removed. An exception occurs: [{}]", e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
    }
}
