package com.astrapay.starter.configuration;

import com.astrapay.starter.dto.ServletDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import net.logstash.logback.marker.ObjectAppendingMarker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * LoggerConfiguration
 *
 * @author Raymond Sugiarto
 * @author Astrapay
 * @version 1.8
 * @since 2021-07-25
 */
@Slf4j
@Component
public class LoggerConfiguration {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String RAW_PAYLOAD = "raw";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_JSON_UTF = "application/json; charset=utf-8";
    public static final String INNER_FIELD_SEPARATOR_REGEX = "\\.";
    public static final int FIRST_INDEX_VALUE = 0;
    public static final int SIZE_SINGLE_VALUE = 1;

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${astrapay-starter.log.request.exclude.required-field:}")
    private List<String> excludedRequiredField;

    @Value("${astrapay-starter.log.request.exclude.field:}")
    private List<String> excludedField;

    @Value("${astrapay-starter.log.request.exclude.param:}")
    private List<String> excludedParams;

    private static final String DELIMITER_CONTENT_TYPE = ";";

    /**
     * logRequest digunakan untuk melakukan ekstrasi HttpServletRequest ke structured logging dalam bentuk JSON
     *
     * @param request
     * @throws IOException
     */
    public ServletDto logRequest(HttpServletRequest request) throws IOException {
        HashMap payload = new HashMap<>();
        Map<Object, Object> requestParams = new HashMap<>();
        String payloadStr = "";

        // Retrieving content type
        Optional<String> contentType = Optional.ofNullable(request.getContentType());

        if ((Arrays.asList(POST, PUT, PATCH).contains(request.getMethod())) && contentType.isPresent()) {
            // Processing payload for POST, PATCH, and PUT methods
            payloadStr = request.getReader().lines().collect(Collectors.joining(""));
            List<String> contentTypes = Arrays.stream(request.getContentType().split(DELIMITER_CONTENT_TYPE)).collect(Collectors.toList());
            if (contentTypes.contains(APPLICATION_JSON)) {
                if (StringUtils.isNotBlank(payloadStr)) {
                    payload = mapper.readValue(mapper.readTree(payloadStr).toString(), HashMap.class);
                }
            } else {
                payload.put(RAW_PAYLOAD, payloadStr);
            }

            replaceExcludeFields(payload, excludedField);
            replaceExcludeFields(payload, excludedRequiredField);
        } else if (GET.equalsIgnoreCase(request.getMethod())) {
            Enumeration<String> parameterNames = request.getParameterNames();

            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                requestParams.put(paramName, paramValues.length == SIZE_SINGLE_VALUE ? paramValues[FIRST_INDEX_VALUE] : Arrays.toString(paramValues));
            }

            replaceExcludeFields(requestParams, excludedParams);
        }

        // Processing headers
        Map<String, String> mapHeaders = new HashMap<>();
        Collections.list(request.getHeaderNames())
                .forEach(headerName -> mapHeaders.put(headerName, request.getHeader(headerName)));

        ServletDto servletDto = ServletDto.builder()
                .uri(request.getRequestURI())
                .remoteAddress(request.getRemoteAddr())
                .header(mapHeaders)
                .payloadSize(payloadStr.length())
                .payload(payload)
                .params(requestParams)
                .method(request.getMethod())
                .contentType(request.getContentType())
                .build();

        log.info(new ObjectAppendingMarker("request", servletDto), servletDto.toString());
        return servletDto;
    }

    private void replaceExcludeFields(Map<Object, Object> map, List<String> excludedFields) {
        if (!excludedFields.isEmpty()) {
            for (String field : excludedFields) {
                replaceField(map, Arrays.asList(field.split(INNER_FIELD_SEPARATOR_REGEX)), FIRST_INDEX_VALUE);
            }
        }
    }

    private void replaceField(Map<Object, Object> map, List<String> fields, int index) {
        Object key = fields.get(index);
        Object value = map.get(key);

        if (Objects.nonNull(value)) {
            if (value instanceof Map) {
                replaceField((Map<Object, Object>) value, fields, index + SIZE_SINGLE_VALUE);
            } else if (!(value instanceof List)) {
                map.put(key, "[****]");
            }
        }
    }

}
