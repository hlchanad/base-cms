package com.chanhonlun.basecms.filter.wrapper;

import com.chanhonlun.basecms.util.WrapperUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public LogRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = WrapperUtil.getRequestStringFromInputStream(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return WrapperUtil.getServletInputStream(new ByteArrayInputStream(body.getBytes()));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}