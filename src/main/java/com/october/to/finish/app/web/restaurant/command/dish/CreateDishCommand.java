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
import java.time.LocalDateTime;

public class CreateDishCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(CreateDishCommand.class);
    private final DishService dishService;

    public CreateDishCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        Dish dish = null;
        try {
            dish = Dish.newBuilder().setTitle(request.getParameter("title")).
                    setDescription(request.getParameter("description")).
                    setPrice(Double.parseDouble(request.getParameter("price"))).
                    setWeight(Integer.parseInt(request.getParameter("weight"))).
                    setCooking(Integer.parseInt(request.getParameter("timeToCreate"))).
                    setDateCreated(LocalDateTime.now()).
                    setCategory(Dish.Category.valueOf(request.getParameter("dishCategory").toUpperCase())).
                    build();
            LOGGER.info("[CreateDishCommand] Dish from view : {};",dish);
            dishService.save(dish);
            LOGGER.info("[CreateDishCommand] Dish saved : {}", dish);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while saving Dish");
            throw new CommandException(e.getMessage(), e);
        }
        return "home.jsp";
    }
}
