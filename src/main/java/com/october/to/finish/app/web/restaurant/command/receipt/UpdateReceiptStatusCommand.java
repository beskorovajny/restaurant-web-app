package com.october.to.finish.app.web.restaurant.command.receipt;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateReceiptStatusCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(UpdateReceiptStatusCommand.class);
    private final ReceiptService receiptService;
    public UpdateReceiptStatusCommand(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        long receiptId = Long.parseLong(request.getParameter("receiptId"));
        try {
            Receipt receipt = receiptService.findById(receiptId);
            if (receipt != null && receipt.getId() > 0) {
                if (Receipt.Status.NEW.equals(receipt.getStatus())) {
                    receipt.setStatus(Receipt.Status.COOKING);
                } else if (Receipt.Status.COOKING.equals(receipt.getStatus())) {
                    receipt.setStatus(Receipt.Status.DELIVERY);
                } else if(Receipt.Status.DELIVERY.equals(receipt.getStatus())){
                    receipt.setStatus(Receipt.Status.COMPLETED);
                }
                receiptService.update(receipt.getId(), receipt);
                LOGGER.info("[UpdateReceiptStatusCommand] Receipt status for ID:[{}] updated.", receipt.getId());
            }
        } catch (ServiceException e) {
            LOGGER.error("[UpdateReceiptStatusCommand] Failed to update receipt status for ID:[{}].", receiptId);
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=admin";
    }
}
