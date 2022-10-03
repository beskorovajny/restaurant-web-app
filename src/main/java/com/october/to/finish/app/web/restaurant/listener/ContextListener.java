package com.october.to.finish.app.web.restaurant.listener;

import com.october.to.finish.app.web.restaurant.command.*;
import com.october.to.finish.app.web.restaurant.command.dish.CreateDishCommand;
import com.october.to.finish.app.web.restaurant.command.dish.DishFormCommand;
import com.october.to.finish.app.web.restaurant.command.dish.MenuCommand;
import com.october.to.finish.app.web.restaurant.command.user.*;
import com.october.to.finish.app.web.restaurant.dao.*;
import com.october.to.finish.app.web.restaurant.dao.connections.ConnectionPoolHolder;
import com.october.to.finish.app.web.restaurant.dao.impl.*;
import com.october.to.finish.app.web.restaurant.service.*;
import com.october.to.finish.app.web.restaurant.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements HttpSessionListener, ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);
    private static final String CONTEXT_LISTENER_MSG = "[ContextListener]";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // empty
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // empty
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("[ContextListener] Context initialization started...");
        ServletContext context = sce.getServletContext();
        context.setInitParameter("encoding", "UTF-8");
        try {
            initServices(context);
            LOGGER.info("{} Services initialized", CONTEXT_LISTENER_MSG);
        } catch (SQLException e) {
            LOGGER.error("{} Services initialization failed!..", CONTEXT_LISTENER_MSG);
            throw new RuntimeException(e);
        }
    }

    private void initServices(ServletContext context) throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        LOGGER.info("{} Connection created. {}", CONTEXT_LISTENER_MSG, connection.getMetaData());

        UserDAO userDAO = new UserDAOImpl(connection);
        LOGGER.info("{} UserDAO created.", CONTEXT_LISTENER_MSG);

        ReceiptDAO receiptDAO = new ReceiptDAOImpl(connection);
        LOGGER.info("{} ReceiptDAO created.", CONTEXT_LISTENER_MSG);

        AddressDAO addressDAO = new AddressDAOImpl(connection);
        LOGGER.info("{} AddressDAO created.", CONTEXT_LISTENER_MSG);

        DishDAO dishDAO = new DishDAOImpl(connection);
        LOGGER.info("{} DishDAO created.", CONTEXT_LISTENER_MSG);

        UserService userService = new UserServiceImpl(userDAO);
        context.setAttribute("userService", userService);
        LOGGER.info("{} UserService created.", CONTEXT_LISTENER_MSG);

        ReceiptService receiptService = new ReceiptServiceImpl(receiptDAO);
        context.setAttribute("receiptService", receiptService);
        LOGGER.info("{} ReceiptService created.", CONTEXT_LISTENER_MSG);

        AddressService addressService = new AddressServiceImpl(addressDAO);
        context.setAttribute("addressService", addressService);
        LOGGER.info("{} AddressService created.", CONTEXT_LISTENER_MSG);

        DishService dishService = new DishServiceImpl(dishDAO);
        context.setAttribute("dishService", addressService);
        LOGGER.info("{} DishService created.", CONTEXT_LISTENER_MSG);

        CommandContainer commandContainer = new CommandContainer();
        LOGGER.info("{} CommandContainer created.", CONTEXT_LISTENER_MSG);

        AppCommand appCommand = new HomeCommand();
        commandContainer.addCommand("home", appCommand);
        commandContainer.addCommand("", appCommand);
        commandContainer.addCommand(null, appCommand);

        appCommand = new LanguageCommand();
        commandContainer.addCommand("setLang", appCommand);
        LOGGER.info("{} Language command created.", CONTEXT_LISTENER_MSG);

        appCommand = new ExceptionCommand();
        commandContainer.addCommand("error", appCommand);
        LOGGER.info("{} Error command created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationFormCommand();
        commandContainer.addCommand("registrationForm", appCommand);
        LOGGER.info("{} RegistrationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationCommand(userService);
        commandContainer.addCommand("registration", appCommand);
        LOGGER.info("{} Registration command created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginFormCommand();
        commandContainer.addCommand("loginForm", appCommand);
        LOGGER.info("{} LoginFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginCommand(userService);
        commandContainer.addCommand("login", appCommand);
        LOGGER.info("{} LoginCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AdminCommand(receiptService, dishService, userService);
        commandContainer.addCommand("admin", appCommand);
        LOGGER.info("{} AdminCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new DishFormCommand();
        commandContainer.addCommand("dishForm", appCommand);
        LOGGER.info("{} DishFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateDishCommand(dishService);
        commandContainer.addCommand("createDish", appCommand);
        LOGGER.info("{} CreateDishCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new MenuCommand(dishService);
        commandContainer.addCommand("menu", appCommand);
        LOGGER.info("{} MenuCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LogoutCommand();
        commandContainer.addCommand("logout", appCommand);
        LOGGER.info("{} LogoutCommand created.", CONTEXT_LISTENER_MSG);



        context.setAttribute("commandContainer", commandContainer);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // empty
    }
}
