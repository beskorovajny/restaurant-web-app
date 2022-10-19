package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.service.DishService;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements AppCommand {
    private static final Logger LOGGER = LogManager.getLogger(AdminCommand.class);
    private final ReceiptService receiptService;
    private final DishService dishService;
    private final UserService userService;

    public AdminCommand(ReceiptService receiptService, DishService dishService, UserService userService) {
        this.receiptService = receiptService;
        this.dishService = dishService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        int page;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<Receipt> receipts = null;
        try {
            receipts = receiptService.findAll(page);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("receipts", receipts);
        int countPages = receiptService.getRecordsCount() / 10 + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        return "admin.jsp";
    }
}
