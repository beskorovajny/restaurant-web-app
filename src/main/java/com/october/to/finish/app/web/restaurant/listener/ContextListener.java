package com.october.to.finish.app.web.restaurant.listener;

import com.october.to.finish.app.web.restaurant.command.*;
import com.october.to.finish.app.web.restaurant.command.dish.*;
import com.october.to.finish.app.web.restaurant.command.receipt.ReceiptDetailsCommand;
import com.october.to.finish.app.web.restaurant.command.receipt.RemoveReceiptCommand;
import com.october.to.finish.app.web.restaurant.command.receipt.UpdateReceiptStatusCommand;
import com.october.to.finish.app.web.restaurant.command.user.*;
import com.october.to.finish.app.web.restaurant.dao.ContactsDAO;
import com.october.to.finish.app.web.restaurant.dao.DishDAO;
import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.dao.connections.ConnectionPoolHolder;
import com.october.to.finish.app.web.restaurant.dao.impl.ContactsDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.DishDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.ReceiptDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.UserDAOImpl;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import com.october.to.finish.app.web.restaurant.service.DishService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.service.UserService;
import com.october.to.finish.app.web.restaurant.service.impl.ContactsServiceImpl;
import com.october.to.finish.app.web.restaurant.service.impl.DishServiceImpl;
import com.october.to.finish.app.web.restaurant.service.impl.ReceiptServiceImpl;
import com.october.to.finish.app.web.restaurant.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class ContextListener implements HttpSessionListener, ServletContextListener {
    private static final Logger log = LogManager.getLogger(ContextListener.class);
    private static final String CONTEXT_LISTENER_MSG = "[ContextListener]";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("[ContextListener] Context initialization started...");
        ServletContext context = sce.getServletContext();
        context.setInitParameter("encoding", "UTF-8");
        try {
            initServices(context);
            log.info("{} Services initialized", CONTEXT_LISTENER_MSG);
        } catch (SQLException e) {
            log.error("{} Services initialization failed!..", CONTEXT_LISTENER_MSG);
            throw new RuntimeException(e);
        }
    }

    private void initServices(ServletContext context) throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        log.info("{} Connection created. {}", CONTEXT_LISTENER_MSG, connection.getMetaData());

        UserDAO userDAO = new UserDAOImpl(connection);
        log.info("{} UserDAO created.", CONTEXT_LISTENER_MSG);

        ReceiptDAO receiptDAO = new ReceiptDAOImpl(connection);
        log.info("{} ReceiptDAO created.", CONTEXT_LISTENER_MSG);

        ContactsDAO contactsDAO = new ContactsDAOImpl(connection);
        log.info("{} ContactsDAO created.", CONTEXT_LISTENER_MSG);

        DishDAO dishDAO = new DishDAOImpl(connection);
        log.info("{} DishDAO created.", CONTEXT_LISTENER_MSG);

        UserService userService = new UserServiceImpl(userDAO);
        context.setAttribute("userService", userService);
        log.info("{} UserService created.", CONTEXT_LISTENER_MSG);

        ReceiptService receiptService = new ReceiptServiceImpl(receiptDAO, contactsDAO);
        context.setAttribute("receiptService", receiptService);
        log.info("{} ReceiptService created.", CONTEXT_LISTENER_MSG);

        ContactsService contactsService = new ContactsServiceImpl(contactsDAO);
        context.setAttribute("addressService", contactsService);
        log.info("{} ContactsService created.", CONTEXT_LISTENER_MSG);

        DishService dishService = new DishServiceImpl(dishDAO);
        context.setAttribute("dishService", contactsService);
        log.info("{} DishService created.", CONTEXT_LISTENER_MSG);

        CommandContainer commandContainer = new CommandContainer();
        log.info("{} CommandContainer created.", CONTEXT_LISTENER_MSG);

        AppCommand appCommand = new HomeCommand();
        commandContainer.addCommand("home", appCommand);
        commandContainer.addCommand("", appCommand);
        commandContainer.addCommand(null, appCommand);

        appCommand = new LanguageCommand();
        commandContainer.addCommand("setLang", appCommand);
        log.info("{} Language command created.", CONTEXT_LISTENER_MSG);

        appCommand = new ExceptionCommand();
        commandContainer.addCommand("error", appCommand);
        log.info("{} Error command created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationFormCommand();
        commandContainer.addCommand("registration_form", appCommand);
        log.info("{} RegistrationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationCommand(userService);
        commandContainer.addCommand("registration", appCommand);
        log.info("{} Registration command created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginFormCommand();
        commandContainer.addCommand("login_form", appCommand);
        log.info("{} LoginFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginCommand(userService);
        commandContainer.addCommand("login", appCommand);
        log.info("{} LoginCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AdminCommand(receiptService);
        commandContainer.addCommand("admin", appCommand);
        log.info("{} AdminCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new DishFormCommand();
        commandContainer.addCommand("dish_form", appCommand);
        log.info("{} DishFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllDishesCommand(dishService);
        commandContainer.addCommand("dishes", appCommand);
        log.info("{} AllDishesCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new MenuCommand(dishService);
        commandContainer.addCommand("menu", appCommand);
        log.info("{} MenuCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllDishesSortedByPriceCommand(dishService);
        commandContainer.addCommand("sorted_by_price", appCommand);
        log.info("{} AllDishesSortedByPriceCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllDishesSortedByTitleCommand(dishService);
        commandContainer.addCommand("sorted_by_title", appCommand);
        log.info("{} AllDishesSortedByTitleCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllDishesSortedByCategoryCommand(dishService);
        commandContainer.addCommand("sorted_by_category", appCommand);
        log.info("{} AllDishesSortedByCategoryCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllDishesFilteredByCategoryCommand(dishService);
        commandContainer.addCommand("filtered_dishes", appCommand);
        log.info("{} AllDishesFilteredByCategoryCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateDishCommand(dishService);
        commandContainer.addCommand("create_dish", appCommand);
        log.info("{} CreateDishCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RemoveDishCommand(dishService);
        commandContainer.addCommand("remove_dish", appCommand);
        log.info("{} RemoveDishCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RemoveReceiptCommand(receiptService);
        commandContainer.addCommand("remove_receipt", appCommand);
        log.info("{} RemoveReceiptCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RemoveUserCommand(userService);
        commandContainer.addCommand("remove_user", appCommand);
        log.info("{} RemoveUserCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new UserReceiptsCommand(receiptService, contactsService);
        commandContainer.addCommand("user_receipts", appCommand);
        log.info("{} UserReceiptsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllUsersCommand(userService);
        commandContainer.addCommand("users", appCommand);
        log.info("{} AllUsersCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LogoutCommand();
        commandContainer.addCommand("logout", appCommand);
        log.info("{} LogoutCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditDishFormCommand(dishService);
        commandContainer.addCommand("edit_dish_form", appCommand);
        log.info("{} EditDishFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditDishCommand(dishService);
        commandContainer.addCommand("edit_dish", appCommand);
        log.info("{} EditDishCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new ChangeUserRoleCommand(userService);
        commandContainer.addCommand("change_user_role", appCommand);
        log.info("{} ChangeUserRoleCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new UpdateReceiptStatusCommand(receiptService);
        commandContainer.addCommand("update_receipt_status", appCommand);
        log.info("{} UpdateReceiptStatusCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AddToCartCommand(dishService);
        commandContainer.addCommand("add_to_cart", appCommand);
        log.info("{} AddToCartCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CheckoutFormCommand(dishService);
        commandContainer.addCommand("checkout_form", appCommand);
        log.info("{} CheckoutFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CheckoutCommand(receiptService);
        commandContainer.addCommand("checkout", appCommand);
        log.info("{} CheckoutCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CleanCartCommand();
        commandContainer.addCommand("clean_cart", appCommand);
        log.info("{} CleanCartCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new ReceiptDetailsCommand(receiptService, contactsService, userService);
        commandContainer.addCommand("receipt_details", appCommand);
        log.info("{} ReceiptDetailsCommand created.", CONTEXT_LISTENER_MSG);

        context.setAttribute("commandContainer", commandContainer);

    }
}
