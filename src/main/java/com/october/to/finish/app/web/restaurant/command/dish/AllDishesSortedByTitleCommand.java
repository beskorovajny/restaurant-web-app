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

public class AllDishesSortedByTitleCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(AllDishesSortedByTitleCommand.class);
    private static final String ALL_DISH_COMMAND_MSG = "[AllDishesCommand]";
    private final DishService dishService;

    public AllDishesSortedByTitleCommand(DishService dishService) {
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
        try {
            dishes = dishService.findAllSortedByTitle(page);
            LOGGER.info("{} Dishes sorted by title found.", ALL_DISH_COMMAND_MSG);
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive sorted by title dishes! An exception occurs: [{}]",
                    ALL_DISH_COMMAND_MSG, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("dishes", dishes);
        int countPages = dishService.getRecordsCount() / 10 + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        return "menu.jsp";
    }
}
