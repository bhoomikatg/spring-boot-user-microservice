package btg.spring.boot.userdetails.service;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log
public class LoggingServiceImpl implements LoggingService {

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("REQUEST ").append("method=[").append(httpServletRequest.getMethod()).append("] ").append("path=[").append(httpServletRequest.getRequestURI()).append("] ").
                append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }

        log.info(stringBuilder.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("RESPONSE ").append("method=[").append(httpServletRequest.getMethod()).append("] ").append("path=[").append(httpServletRequest.getRequestURI()).append("] ")
                .append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ")
                .append("responseBody=[").append(body).append("] ");

        log.info(stringBuilder.toString());
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }
        return map;
    }
}