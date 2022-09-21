package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private final UserService userService;
    public LoginCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        String page = null;
        HttpSession session = request.getSession();
        String email = request.getParameter("login");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = userService.findByEmail(email);
        } catch (ServiceException e) {
            throw new FatalApplicationException(e.getMessage(), e);
        }
        if(user != null && PasswordEncryptionUtil.validate(password, String.valueOf(user.getPassword()))){
            session.setAttribute("user", user);
            if (user.getRole() == User.Role.MANAGER) {
                page = "managerPanel.jsp";
            }
            if (user.getRole() == User.Role.CLIENT) {
                page = "personalCabinet.jsp";
            }
        } else {
            LOGGER.info("Cannot Login");
            return "errorPage.jsp";
        }
        return page;
    }
}
