package com.october.to.finish.app.web.restaurant.command.receipt;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import com.october.to.finish.app.web.restaurant.service.DishService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReceiptDetailsCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptDetailsCommand.class);
    private final ReceiptService receiptService;
    private final ContactsService contactsService;
    private final DishService dishService;
    private final UserService userService;

    public ReceiptDetailsCommand(ReceiptService receiptService, ContactsService contactsService,
                                 DishService dishService, UserService userService) {
        this.receiptService = receiptService;
        this.contactsService = contactsService;
        this.dishService = dishService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {

        return "receipt_details.jsp";
    }
}
