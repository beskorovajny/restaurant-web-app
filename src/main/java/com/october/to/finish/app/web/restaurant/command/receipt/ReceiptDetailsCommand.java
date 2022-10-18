package com.october.to.finish.app.web.restaurant.command.receipt;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReceiptDetailsCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptDetailsCommand.class);
    private final ReceiptService receiptService;
    public ReceiptDetailsCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        return null;
    }
}