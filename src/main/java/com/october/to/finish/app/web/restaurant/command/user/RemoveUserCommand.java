package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveUserCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(RemoveUserCommand.class);
    private final UserService userService;

    public RemoveUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            userService.delete(userId);
            log.info("[RemoveUserCommand] User for ID:[{}] successfully removed.", userId);
        } catch (ServiceException e) {
            log.error("[RemoveUserCommand] Failed to remove user for ID:[{}]", userId);
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=users";
    }
}
