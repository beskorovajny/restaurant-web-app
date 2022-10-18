package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CheckoutCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(CheckoutCommand.class);
    private static final String CHECKOUT_MSG = "[CheckoutCommand]";
    private final ReceiptService receiptService;
    private final UserService userService;
    private final ContactsService contactsService;

    public CheckoutCommand(ReceiptService receiptService, UserService userService, ContactsService contactsService) {
        this.receiptService = receiptService;
        this.userService = userService;
        this.contactsService = contactsService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        HttpSession session = request.getSession();
        User user = null;
        Map<Dish, Integer> cart = null;
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        }
        LOGGER.debug("{} User from session: [{}]", CHECKOUT_MSG, user);
        if (session.getAttribute("cart") != null) {
            cart = (Map<Dish, Integer>) session.getAttribute("cart");
        }
        LOGGER.debug("{} Cart from session: [{}]", CHECKOUT_MSG, cart);
        String country = request.getParameter("country");
        LOGGER.debug("Country from request: [{}]", country);
        String city = request.getParameter("city");
        LOGGER.debug("City from request: [{}]", city);
        String street = request.getParameter("street");
        LOGGER.debug("Street from request: [{}]", street);
        String building = request.getParameter("building");
        LOGGER.debug("Building from request: [{}]", building);
        String phoneNumber = request.getParameter("phone");
        LOGGER.debug("Phone from request: [{}]", phoneNumber);
        Contacts contacts = null;
        if ((country != null && !country.isEmpty()) && (city != null && !city.isEmpty()) &&
                (street != null && !street.isEmpty()) && (building != null && !building.isEmpty()) &&
                (phoneNumber != null && !phoneNumber.isEmpty())) {
            contacts = new Contacts(country, city, street, building, phoneNumber);
        }
        LOGGER.debug("{} Contacts info from request: [{}]", CHECKOUT_MSG, contacts);
        if (user != null && contacts != null) {
            try {
                receiptService.setDishesForReceipt(cart, contacts, user.getId());
                LOGGER.info("{} Receipt saved, dishes assigned.", CHECKOUT_MSG);
                session.setAttribute("cart", new HashMap<Dish, Integer>());
            } catch (ServiceException | DAOException e) {
                LOGGER.error("{} Failed to save receipt or assign dishes. An exception occurs: [{}]",
                        CHECKOUT_MSG, e.getMessage());
                throw new CommandException(e.getMessage(), e);
            }
        }
        return "home.jsp";
    }
}
