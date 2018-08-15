package com.chanhonlun.springboottest.filter;

import com.chanhonlun.springboottest.filter.wrapper.LogResponseWrapper;
import com.chanhonlun.springboottest.filter.wrapper.LogRequestWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Order(2)
@Component
public class LogRequestResponseFilter implements Filter {

    private static final Logger paramLogger = LoggerFactory.getLogger(LogRequestResponseFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest  httpServletRequest  = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        LogRequestWrapper  requestWrapper  = new LogRequestWrapper(httpServletRequest);
        LogResponseWrapper responseWrapper = new LogResponseWrapper(httpServletResponse);

        paramLogger.info("request headers: {}", getHeaders(Collections.list(httpServletRequest.getHeaderNames()), httpServletRequest::getHeader));
        if (HttpMethod.GET.matches(httpServletRequest.getMethod())) {
            paramLogger.info("request query  : {}", httpServletRequest.getQueryString());
        } else {
            if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(httpServletRequest.getContentType())) {
                String jsonString = requestWrapper.getBody();
                JsonNode jsonNode = new ObjectMapper().readValue(jsonString, JsonNode.class);
                paramLogger.info("request body   : {}", jsonNode.toString()); // transform to compact json
            } else {
                paramLogger.info("request body   : {}", requestWrapper.getBody());
            }
        }

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
            responseWrapper.flushBuffer();
        } finally {
            paramLogger.info("response headers: {}", getHeaders(httpServletResponse.getHeaderNames(), httpServletResponse::getHeader));
            paramLogger.info("response body   : {}", new String(responseWrapper.getCopy(), StandardCharsets.UTF_8));
        }

    }

    @Override
    public void destroy() {
    }

    private Map<String, String> getHeaders(Collection<String> headerNames, Function<String, String> getHeader) {
        return headerNames.stream()
                .map(headerName -> new ImmutablePair<>(headerName, getHeader.apply(headerName)))
                .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue));
    }
}
