package com.october.to.finish.app.web.restaurant.command.user;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.service.DishService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CheckoutFormCommand implements AppCommand {
    private final DishService dishService;

    public CheckoutFormCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        HttpSession session = request.getSession();
        Map<Dish, Integer> cart = null;
        if (session.getAttribute("cart") != null) {
            cart = (Map<Dish, Integer>) session.getAttribute("cart");

            Map<Dish, Integer> actualCart = new HashMap<>();
            for (Map.Entry<Dish, Integer> entry : cart.entrySet()) {
                try {
                    if (dishService.findById(entry.getKey().getId()).getId() != 0) {
                        actualCart.put(entry.getKey(), entry.getValue());
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e.getMessage(), e);
                }
            }
            session.setAttribute("cart", actualCart);
        }
        return "checkout.jsp";
    }
}
