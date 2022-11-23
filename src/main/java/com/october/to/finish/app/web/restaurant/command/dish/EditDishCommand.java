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

public class EditDishCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(EditDishCommand.class);
    private final DishService dishService;

    public EditDishCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String weight = request.getParameter("weight");
        String cooking = request.getParameter("cooking");
        String category = request.getParameter("category");
        try {
            Dish updated = dishService.findById(Long.parseLong(request.getParameter("dishId")));
            Dish actual = dishService.findById(Integer.parseInt(request.getParameter("dishId")));

            if (title == null || title.isEmpty()) {
                updated.setTitle(actual.getTitle());
            } else {
                updated.setTitle(title);
            }

            if (description == null || description.isEmpty()) {
                updated.setDescription(actual.getDescription());
            } else {
                updated.setDescription(description);
            }

            if (price == null || price.isEmpty()) {
                updated.setPrice(actual.getPrice());
            } else {
                updated.setPrice(Double.parseDouble(price));
            }

            if (weight == null || weight.isEmpty()) {
                updated.setWeight(actual.getWeight());
            } else {
                updated.setWeight(Integer.parseInt(weight));
            }

            if (cooking == null || cooking.isEmpty()) {
                updated.setCooking(actual.getCooking());
            } else {
                updated.setCooking(Integer.parseInt(cooking));
            }

            if (category == null || category.isEmpty()) {
                updated.setCategory(actual.getCategory());
            } else {
                updated.setCategory(Dish.Category.valueOf(category.toUpperCase()));
            }
            updated.setDateCreated(LocalDateTime.now());

            dishService.update(actual.getId(), updated);
            return "controller?command=dishes";
        } catch (ServiceException e) {
            log.error("[EditDishCommand] Can't update dish");
            throw new CommandException(e.getMessage(), e);
        }
    }
}
