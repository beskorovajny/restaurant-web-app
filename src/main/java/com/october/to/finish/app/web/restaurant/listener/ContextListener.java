package com.october.to.finish.app.web.restaurant.listener;

import com.october.to.finish.app.web.restaurant.command.*;
import com.october.to.finish.app.web.restaurant.command.dish.*;
import com.october.to.finish.app.web.restaurant.command.user.*;
import com.october.to.finish.app.web.restaurant.dao.AddressDAO;
import com.october.to.finish.app.web.restaurant.dao.DishDAO;
import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.dao.connections.ConnectionPoolHolder;
import com.october.to.finish.app.web.restaurant.dao.impl.AddressDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.DishDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.ReceiptDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.UserDAOImpl;
import com.october.to.finish.app.web.restaurant.service.AddressService;
import com.october.to.finish.app.web.restaurant.service.DishService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.service.UserService;
import com.october.to.finish.app.web.restaurant.service.impl.AddressServiceImpl;
import com.october.to.finish.app.web.restaurant.service.impl.DishServiceImpl;
import com.october.to.finish.app.web.restaurant.service.impl.ReceiptServiceImpl;
import com.october.to.finish.app.web.restaurant.service.impl.UserServiceImpl;
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
        commandContainer.addCommand("registration_form", appCommand);
        LOGGER.info("{} RegistrationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationCommand(userService);
        commandContainer.addCommand("registration", appCommand);
        LOGGER.info("{} Registration command created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginFormCommand();
        commandContainer.addCommand("login_form", appCommand);
        LOGGER.info("{} LoginFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginCommand(userService);
        commandContainer.addCommand("login", appCommand);
        LOGGER.info("{} LoginCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AdminCommand(receiptService, dishService, userService);
        commandContainer.addCommand("admin", appCommand);
        LOGGER.info("{} AdminCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new DishFormCommand();
        commandContainer.addCommand("dish_form", appCommand);
        LOGGER.info("{} DishFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllDishesCommand(dishService);
        commandContainer.addCommand("dishes", appCommand);
        LOGGER.info("{} AllDishesCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateDishCommand(dishService);
        commandContainer.addCommand("create_dish", appCommand);
        LOGGER.info("{} CreateDishCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RemoveDishCommand(dishService);
        commandContainer.addCommand("remove_dish", appCommand);
        LOGGER.info("{} RemoveDishCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new MenuCommand(dishService);
        commandContainer.addCommand("menu", appCommand);
        LOGGER.info("{} MenuCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new UserReceiptsCommand(receiptService, addressService);
        commandContainer.addCommand("user_receipts", appCommand);
        LOGGER.info("{} UserReceiptsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllUsersCommand(userService);
        commandContainer.addCommand("users", appCommand);
        LOGGER.info("{} AllUsersCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LogoutCommand();
        commandContainer.addCommand("logout", appCommand);
        LOGGER.info("{} LogoutCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditDishFormCommand(dishService);
        commandContainer.addCommand("edit_dish_form", appCommand);
        LOGGER.info("{} EditDishFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditDishCommand(dishService);
        commandContainer.addCommand("edit_dish", appCommand);
        LOGGER.info("{} EditDishCommand created.", CONTEXT_LISTENER_MSG);

        context.setAttribute("commandContainer", commandContainer);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // empty
    }
}
