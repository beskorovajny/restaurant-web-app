package com.october.to.finish.app.web.restaurant.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("locale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("locale"));
        }
        chain.doFilter(request, response);
    }
}
