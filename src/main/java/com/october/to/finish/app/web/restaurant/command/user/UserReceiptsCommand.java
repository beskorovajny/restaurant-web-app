package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UserReceiptsCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(UserReceiptsCommand.class);
    private final ReceiptService receiptService;
    private final ContactsService contactsService;

    public UserReceiptsCommand(ReceiptService receiptService, ContactsService contactsService) {
        this.receiptService = receiptService;
        this.contactsService = contactsService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        HttpSession session = request.getSession();
        int page;

        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Receipt> receipts = null;
        User user = (User) session.getAttribute("user");
        int countPages = 0;
        try {
            receipts = receiptService.findAllByUser(user.getId(), page);
            countPages = receiptService.getRecordsCount() / 10 + 1;
        } catch (ServiceException | DAOException e) {
            throw new CommandException(e.getMessage(), e);
        }

        request.setAttribute("receipts", receipts);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        return "my_receipts.jsp";
    }
}
