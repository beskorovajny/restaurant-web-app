package com.october.to.finish.app.web.restaurant.filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.jsp", "/controller"},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class JspEncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(JspEncodingFilter.class);
    public static final String ENCODING = "encoding";
    private String code;

    @Override
    public void init(FilterConfig filterConfig) {
        code = filterConfig.getInitParameter(ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("[JspEncodingFilter] Filter started {},  {}", request.getParameterMap(), response);
        String codeRequest = request.getCharacterEncoding();
        LOGGER.info("[JspEncodingFilter] request code : {}", codeRequest);
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
