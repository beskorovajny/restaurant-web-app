package com.october.to.finish.app.web.restaurant.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class JspEncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(JspEncodingFilter.class);
    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) encoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("[JspEncodingFilter] Filter started {},  {}", request.getParameterMap(), response);
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
            LOGGER.info("[JspEncodingFilter] encoding: {}", encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
