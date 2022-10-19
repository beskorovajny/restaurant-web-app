package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
            if (user.getEmail() == null) {
                LOGGER.info("Cannot Login");
                page = "controller?command=registration_form";
                return page;
            }
            if (PasswordEncryptionUtil.validate(PasswordEncryptionUtil.getEncrypted(password)
                    , String.valueOf(user.getPassword()))) {
                LOGGER.info("{} User received from db: {}", LOGIN_COMMAND, user.getEmail());

                Map<Dish, Integer> cart = new HashMap<>();
                session.setAttribute("user", user);

                if (user.getRole().getId() == User.Role.MANAGER.getId()) {
                    page = "controller?command=admin";
                }
                if (user.getRole().getId() == User.Role.CLIENT.getId()) {
                    session.setAttribute("cart", cart);
                    LOGGER.info("{} Cart to session: [{}]", LOGIN_COMMAND, cart);
                    page = "controller?command=menu";
                }
            } else {
                page = "controller?command=login_form";
            }
            return page;
        } catch (ServiceException e) {
            LOGGER.error("{} An exception occurs : {}", LOGIN_COMMAND, e.getMessage());
            throw new FatalApplicationException(e.getMessage(), e);
        }
    }
}
