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

public class ChangeUserRoleCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(ChangeUserRoleCommand.class);
    private final UserService userService;

    public ChangeUserRoleCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        long userId = Long.parseLong(request.getParameter("userId"));
        try {
            User user = userService.findById(userId);
            if (user != null && user.getId() != 0) {
                if (User.Role.CLIENT.equals(user.getRole())) {
                    user.setRole(User.Role.MANAGER);
                } else if (User.Role.MANAGER.equals(user.getRole())) {
                    user.setRole(User.Role.CLIENT);
                }
                userService.update(user.getId(), user);
                log.info("[ChangeUserRoleCommand] User.Role for userID:[{}] updated.", user.getId());
            }
        } catch (ServiceException e) {
            log.error("[ChangeUserRoleCommand] Failed to update User.Role for userID:[{}].", userId);
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=users";
    }
}
