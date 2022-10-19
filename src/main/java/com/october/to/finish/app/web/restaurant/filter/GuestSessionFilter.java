package com.october.to.finish.app.web.restaurant.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@WebFilter(filterName = "GuestSessionFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "restricted", value = "/restaurant_web_app/controller?command=admin," +
                "/restaurant_web_app/controller?command=dish_form,/restaurant_web_app/controller?command=dishes," +
                "/restaurant_web_app/controller?command=menu,/restaurant_web_app/controller?command=dishes_sorted_by_price," +
                "/restaurant_web_app/controller?command=dishes_sorted_by_title," +
                "/restaurant_web_app/controller?command=dishes_sorted_by_category," +
                "/restaurant_web_app/controller?command=dishes_filtered,/restaurant_web_app/controller?command=create_dish," +
                "/restaurant_web_app/controller?command=remove_dish,/restaurant_web_app/controller?command=user_receipts," +
                "/restaurant_web_app/controller?command=users,/restaurant_web_app/controller?command=logout," +
                "/restaurant_web_app/controller?command=edit_dish_form,/restaurant_web_app/controller?command=edit_dish," +
                "/restaurant_web_app/controller?command=checkout_form, /restaurant_web_app/controller?command=checkout," +
                "/restaurant_web_app/controller?command=change_user_role, /restaurant_web_app/controller?command=update_receipt_status," +
                "/restaurant_web_app/controller?command=add_to_cart, /restaurant_web_app/controller?command=clean_cart," +
                "/restaurant_web_app/controller?command=remove_receipt, /restaurant_web_app/controller?command=remove_user, " +
                "/restaurant_web_app/controller?command=receipt_details")})
public class GuestSessionFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(GuestSessionFilter.class);
    private List<String> restrictedCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        restrictedCommands = new ArrayList<>();
        String tempCommands = filterConfig.getInitParameter("restricted");
        StringTokenizer token = new StringTokenizer(tempCommands, ",");
        while (token.hasMoreTokens()) {
            restrictedCommands.add(token.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("[GuestSessionFilter] Filter started.");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String requestCommand = req.getRequestURI() + "?" + req.getQueryString();
        boolean shouldBeRestricted = isRestricted(requestCommand);
        if (shouldBeRestricted && req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/controller?command=login_form");
            LOGGER.info("[GuestSessionFilter] Access denied! Redirected to login form.");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isRestricted(String command) {
        for (String restrictedCommand : restrictedCommands) {
            if (command.startsWith(restrictedCommand)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
