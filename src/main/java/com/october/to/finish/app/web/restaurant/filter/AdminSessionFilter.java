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

@WebFilter(filterName = "AdminSessionFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "restricted_adm", value = "/restaurant_web_app/controller?command=admin," +
                "/restaurant_web_app/controller?command=dish_form,/restaurant_web_app/controller?command=dishes," +
                "/restaurant_web_app/controller?command=create_dish," +
                "/restaurant_web_app/controller?command=remove_dish," +
                "/restaurant_web_app/controller?command=users," +
                "/restaurant_web_app/controller?command=edit_dish_form,/restaurant_web_app/controller?command=edit_dish," +
                "/restaurant_web_app/controller?command=remove_receipt, /restaurant_web_app/controller?command=remove_user," +
                "/restaurant_web_app/controller?command=change_user_role, /restaurant_web_app/controller?command=update_receipt_status")})
public class AdminSessionFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AdminSessionFilter.class);
    private List<String> restrictedCommands;
    private User user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        restrictedCommands = new ArrayList<>();
        String tempCommands = filterConfig.getInitParameter("restricted_adm");
        StringTokenizer token = new StringTokenizer(tempCommands, ",");
        while (token.hasMoreTokens()) {
            restrictedCommands.add(token.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("[AdminSessionFilter] Filter started.");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String requestCommand = req.getRequestURI() + "?" + req.getQueryString();
        boolean shouldBeRestricted = isRestricted(requestCommand);
        if (req.getSession().getAttribute("user") != null) {
            user = (User) req.getSession().getAttribute("user");
        }
        if (user != null && shouldBeRestricted && user.getRole() != User.Role.MANAGER) {
            res.sendRedirect(req.getContextPath() + "/controller?command=home");
            log.info("[AdminSessionFilter] Access denied! Redirected to home.");
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
