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
import java.util.ArrayList;
import java.util.List;

public class AllDishesFilteredByCategoryCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(AllDishesFilteredByCategoryCommand.class);
    private static final String ALL_DISH_COMMAND_MSG = "[AllDishesFilteredByCategoryCommand]";
    private final DishService dishService;

    public AllDishesFilteredByCategoryCommand(DishService dishService) {
        this.dishService = dishService;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        int page;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<Dish> dishes = null;
        if (request.getParameter("category") != null) {
            Dish.Category category = Dish.Category.valueOf(request.getParameter("category").toUpperCase());
            try {
                dishes = dishService.findAllFilteredByCategory(category.getId(), page);
                log.info("{} Filtered dishes found.", ALL_DISH_COMMAND_MSG);
            } catch (ServiceException e) {
                log.error("{} Can't receive filtered dishes! An exception occurs: [{}]", ALL_DISH_COMMAND_MSG, e.getMessage());
                throw new CommandException(e.getMessage(), e);
            }
            request.setAttribute("dishes", dishes);
            int countPages = dishService.getRecordsCountForCategory(category.getId()) / 10 + 1;
            List<Integer> pages = new ArrayList<>();
            for (int i = 1; i <= countPages; i++) {
                pages.add(i);
            }
            request.setAttribute("pages", pages);
        }
        return "menu.jsp";
    }
}
