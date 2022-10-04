package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AllUsersCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(AllUsersCommand.class);
    private static final String USERS_COMMAND_MSG = "[AllUsersCommand]";
    private final UserService userService;

    public AllUsersCommand(UserService userService) {
        this.userService = userService;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        int page;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<User> users = null;
        try {
            users = userService.findAll(page);
            LOGGER.info("{} Dishes found.", USERS_COMMAND_MSG);
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive users! An exception occurs: [{}]", USERS_COMMAND_MSG, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("users", users);
        int countPages = userService.getRecordsCount() / 10 + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        return "users.jsp";
    }
}
