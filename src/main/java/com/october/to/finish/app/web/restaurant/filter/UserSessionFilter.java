package com.october.to.finish.app.web.restaurant.filter;

import com.october.to.finish.app.web.restaurant.model.User;
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

@WebFilter(filterName = "UserSessionFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "restricted_user", value = "/restaurant_web_app/controller?command=menu," +
                "/restaurant_web_app/controller?command=user_receipts," +
                "/restaurant_web_app/controller?command=checkout_form, /restaurant_web_app/controller?command=checkout," +
                "/restaurant_web_app/controller?command=add_to_cart, /restaurant_web_app/controller?command=clean_cart")})
public class UserSessionFilter implements Filter {
    private static final Logger log = LogManager.getLogger(UserSessionFilter.class);
    private List<String> restrictedCommands;
    private User user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        restrictedCommands = new ArrayList<>();
        String tempCommands = filterConfig.getInitParameter("restricted_user");
        StringTokenizer token = new StringTokenizer(tempCommands, ",");
        while (token.hasMoreTokens()) {
            restrictedCommands.add(token.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("[UserSessionFilter] Filter started.");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String requestCommand = req.getRequestURI() + "?" + req.getQueryString();
        boolean shouldBeRestricted = isRestricted(requestCommand);
        if (req.getSession().getAttribute("user") != null) {
            user = (User) req.getSession().getAttribute("user");
        }
        if (user != null && shouldBeRestricted && user.getRole() != User.Role.CLIENT) {
            res.sendRedirect(req.getContextPath() + "/controller?command=home");
            log.info("[UserSessionFilter] Access denied! Redirected to home.");
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
