package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MenuCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(MenuCommand.class);
    private static final String MENU_COMMAND_MSG = "[MenuCommand]";
    private final DishService dishService;

    public MenuCommand(DishService dishService) {
        this.dishService = dishService;

    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int page;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<Dish> dishes = null;
        try {
            dishes = dishService.findAll(page);
            log.info("{} Dishes found.", MENU_COMMAND_MSG);
        } catch (ServiceException e) {
            log.error("{} Can't receive dishes! An exception occurs: [{}]", MENU_COMMAND_MSG, e.getMessage());
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
