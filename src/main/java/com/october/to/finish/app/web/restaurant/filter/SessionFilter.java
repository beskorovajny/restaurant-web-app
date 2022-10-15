package com.october.to.finish.app.web.restaurant.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@WebFilter(filterName = "SessionFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "restricted", value = "/restaurant_web_app/controller?command=admin," +
                "/restaurant_web_app/controller?command=dish_form,/restaurant_web_app/controller?command=dishes," +
                "/restaurant_web_app/controller?command=menu,/restaurant_web_app/controller?command=dishes_sorted_by_price," +
                "/restaurant_web_app/controller?command=dishes_sorted_by_title," +
                "/restaurant_web_app/controller?command=dishes_sorted_by_category," +
                "/restaurant_web_app/controller?command=dishes_filtered,/restaurant_web_app/controller?command=create_dish," +
                "/restaurant_web_app/controller?command=remove_dish,/restaurant_web_app/controller?command=user_receipts," +
                "/restaurant_web_app/controller?command=users,/restaurant_web_app/controller?command=logout," +
                "/restaurant_web_app/controller?command=edit_dish_form,/restaurant_web_app/controller?command=edit_dish")})
public class SessionFilter implements Filter {
    private List<String> restrictedCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        restrictedCommands = new ArrayList<>();
        String tempCommands = filterConfig.getInitParameter("restricted");
        StringTokenizer token = new StringTokenizer(tempCommands, ",");
        while (token.hasMoreTokens()){
            restrictedCommands.add(token.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String requestCommand = req.getRequestURI() + "?" + req.getQueryString();
        boolean shouldBeUnavailable = isRestricted(requestCommand);
        if (shouldBeUnavailable && req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/controller?command=login_form");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
    private boolean isRestricted(String command){
        for (String unavailableCommand : restrictedCommands){
            if(command.startsWith(unavailableCommand)){
                return true;
            }
        }
        return false;
    }

    public List<String> getRestrictedCommands(){
        return restrictedCommands;
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
