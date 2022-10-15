package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddToCartCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(AddToCartCommand.class);
    private final DishService dishService;

    public AddToCartCommand(DishService dishService) {
        this.dishService = dishService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        HttpSession session = request.getSession();
        int count = Integer.parseInt(request.getParameter("count"));

        return "menu.jsp";
    }
}
