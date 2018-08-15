package com.chanhonlun.springboottest.filter;

import com.chanhonlun.springboottest.constant.MyHeaders;
import com.chanhonlun.springboottest.constant.SessionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class LogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest  httpServletRequest  = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String threadId = generateThreadID();

        // Server Log Parameter Setting
        MDC.clear();
        MDC.put("threadID", threadId);

        logger.info("request path   : {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());

        httpServletRequest.getSession().setAttribute(SessionAttributes.THREAD_ID, threadId);
        httpServletResponse.setHeader(MyHeaders.X_THREAD_ID, threadId);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
    }

    private String generateThreadID() {
        return Integer.toString(100000 + new Random().nextInt(900000));
    }
}
