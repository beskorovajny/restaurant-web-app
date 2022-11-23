package com.october.to.finish.app.web.restaurant.command.receipt;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveReceiptCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(RemoveReceiptCommand.class);
    private final ReceiptService receiptService;

    public RemoveReceiptCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        long receiptId = Long.parseLong(request.getParameter("receiptId"));
        try {
            receiptService.delete(receiptId);
            log.info("[RemoveReceiptCommand] Receipt for ID:[{}] successfully removed.", receiptId);
        } catch (ServiceException e) {
            log.error("[RemoveReceiptCommand] Failed to remove receipt for ID:[{}]", receiptId);
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=admin";
    }
}
