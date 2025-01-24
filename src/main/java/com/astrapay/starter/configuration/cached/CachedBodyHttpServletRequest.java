package com.astrapay.starter.configuration.cached;

import com.astrapay.starter.dto.ServletDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;
    private final Map<String, String[]> parameterMap;

    @Getter
    @Setter
    private ServletDto servletDto;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        parameterMap = request.getParameterMap(); // <-- This was the crucial part
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameterMap; // this was added just to satisfy spotbugs
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // Create a reader from cachedContent
        // and return it
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}