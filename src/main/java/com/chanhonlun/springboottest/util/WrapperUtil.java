package com.chanhonlun.springboottest.util;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class WrapperUtil {

    public static String getRequestStringFromInputStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] charBuffer = new char[128];
            int    bytesRead  = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }

        return stringBuilder.toString();
    }

    public static ServletInputStream getServletInputStream(final ByteArrayInputStream inputStream) {
        return new ServletInputStream() {
            public int read() {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}
