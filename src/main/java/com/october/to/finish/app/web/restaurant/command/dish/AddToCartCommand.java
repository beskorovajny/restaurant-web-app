package com.october.to.finish.app.web.restaurant.command.dish;

import com.october.to.finish.app.web.restaurant.command.AppCommand;
import com.october.to.finish.app.web.restaurant.exceptions.CommandException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AddToCartCommand implements AppCommand {
    private static final Logger log = LogManager.getLogger(AddToCartCommand.class);
    private final DishService dishService;

    public AddToCartCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        HttpSession session = request.getSession();

        int count = 0;
        if (request.getParameter("count") == null || request.getParameter("count").isEmpty()) {
            count = 1;
        } else {
            count = Integer.parseInt(request.getParameter("count"));
        }
        long dishId = Long.parseLong(request.getParameter("dishId"));
        Map<Dish, Integer> cart = (Map<Dish, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        log.debug("Cart from session: [{}]", cart);
        try {
            Dish dish = dishService.findById(dishId);
            if (dish != null && dish.getId() != 0 && count >= 1) {
                cart.put(dish, count);
                log.info("[AddToCartCommand] Cart successfully updated!");
            }
            session.setAttribute("cart", cart);
            log.debug("Cart to session: [{}]", cart);
        } catch (ServiceException e) {
            log.error("[AddToCartCommand] Failed to add dish to cart! [dishId = {}]...", dishId);
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=menu";
    }
}
