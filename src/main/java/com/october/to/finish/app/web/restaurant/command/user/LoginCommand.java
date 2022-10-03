package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private static final String LOGIN_COMMAND = "[LoginCommand]";
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        String page = null;
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = userService.findByEmail(email);
            LOGGER.info("{} User received from db: {}", LOGIN_COMMAND, user);
        } catch (ServiceException e) {
            LOGGER.error("{} An exception occurs : {}", LOGIN_COMMAND, e.getMessage());
            throw new FatalApplicationException(e.getMessage(), e);
        }
        if (user != null && PasswordEncryptionUtil.validate(PasswordEncryptionUtil.getEncrypted(password)
                , String.valueOf(user.getPassword()))) {
            session.setAttribute("user", user);
            if (user.getRoleId() == User.Role.MANAGER.getId()) {
                page = "controller?command=admin";
            }
            if (user.getRoleId() == User.Role.CLIENT.getId()) {
                page = "home.jsp";
            }
        } else {
            LOGGER.info("Cannot Login");
            return "errorPage.jsp";
        }
        return page;
    }
}
