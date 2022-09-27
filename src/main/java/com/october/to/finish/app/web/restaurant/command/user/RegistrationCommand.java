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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RegistrationCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        User user = null;
        try {
            user = User.newBuilder()
                    .setEmail(request.getParameter("email"))
                    .setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setPhoneNumber(request.getParameter("phoneNumber"))
                    .setPassword(PasswordEncryptionUtil.getEncrypted(request.getParameter("password")).toCharArray())
                    .setRoleId(User.Role.CLIENT.getId())
                    .build();
            LOGGER.info("[RegistrationCommand] User from view : {} + fN: {}", user, request.getParameter("firstName"));
            userService.save(user);
            LOGGER.info("[RegistrationCommand] User saved : {}", user);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while saving User");
            throw new CommandException(e.getMessage(), e);
        }
        return "login.jsp";
    }
}
