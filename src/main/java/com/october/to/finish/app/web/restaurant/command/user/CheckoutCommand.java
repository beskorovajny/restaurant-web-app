package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckoutCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(CheckoutCommand.class);
    private final ReceiptService receiptService;
    private final UserService userService;
    private final ContactsService contactsService;
    public CheckoutCommand(ReceiptService receiptService, UserService userService, ContactsService contactsService){
        this.receiptService = receiptService;
        this.userService = userService;
        this.contactsService = contactsService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        return "receipt_details.jsp";
    }
}
