package com.october.to.finish.app.web.restaurant.command.receipt;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.model.User;
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
    private final UserService userService;

    public ReceiptDetailsCommand(ReceiptService receiptService, ContactsService contactsService, UserService userService) {
        this.receiptService = receiptService;
        this.contactsService = contactsService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        long receiptId = Long.parseLong(request.getParameter("receiptId"));
        try {
            Receipt receipt = receiptService.findById(receiptId);
            receipt.setOrderedDishes(receiptService.findAllOrderedForReceipt(receipt.getId()));
            Contacts contacts = contactsService.findById(receipt.getContactsId());
            User user = userService.findById(receipt.getCustomerId());
            request.setAttribute("user", user);
            request.setAttribute("receipt", receipt);
            request.setAttribute("contacts", contacts);
        } catch (ServiceException e) {
            LOGGER.error("[ReceiptDetailsCommand] Failed to load receipt details");
            throw new CommandException(e.getMessage(), e);
        }
        return "receipt_details.jsp";
    }
}
