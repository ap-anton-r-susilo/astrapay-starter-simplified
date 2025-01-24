package com.astrapay.starter.configuration;

import com.astrapay.starter.configuration.cached.CachedBodyHttpServletRequest;
import com.astrapay.starter.dto.DefaultAttributeError;
import com.astrapay.starter.dto.ServletDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

/**
 * CorrelationConfiguration
 *
 * @author  Raymond Sugiarto
 * @author  Astrapay
 * @version 1.8
 * @since   2021-07-25
 */
@Order(3)
@Component
public class CorrelationConfiguration extends OncePerRequestFilter {

    public static final String CORRELATION_ID_LOG_VAR_NAME = "correlation_id";
    public static final String CORRELATION_ID_HEADER_NAME = "x-correlation-id";

    @Value("${astrapay-starter.log.request.enabled:true}")
    private boolean logRequest;

    @Autowired
    private LoggerConfiguration loggerConfiguration;

    /**
     * FilterInternal ini digunakan untuk
     * <ul>
     *     <li>Melakukan <i>caching</i>  HttpServletRequest sehingga dapat digunakan pada interceptor berikutnya</li>
     *     <li>Melakukan generate CorrelationID sebagai tracking ID pada setiap log yang dilakukan</li>
     *     <li>Melakukan <i>automatic</i> logging request. Log berupa structured logging dalam bentuk JSON</li>
     * </ul>
     *
     * Log Request dapat dimatikan melalui properties <u>astrapay-starter.log.request.enabled</u>
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {

        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

            // Get correlation ID (or generate if not present)
            final String requestCorrelationId = extractOrGenerateCorrelationId(cachedBodyHttpServletRequest);

            // Set logging MDC
            MDC.put(CORRELATION_ID_LOG_VAR_NAME, requestCorrelationId);

            if (this.logRequest) {
                ServletDto servletDto = loggerConfiguration.logRequest(cachedBodyHttpServletRequest);
                cachedBodyHttpServletRequest.setServletDto(servletDto);
            }

            // Carry on using existing spring filter chain
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
        }
        catch (JsonParseException ex){
            //handle jsonParse request
            handleJsonParseException(request, response, ex);
        }
        catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * Get correction ID from current request or generate new correlation ID
     *
     * @param {@link HttpServletRequest} to extract correlation ID from
     *
     * @return {@link String} of the correction ID
     */
    private String extractOrGenerateCorrelationId(final HttpServletRequest request) {
        String headerCorrelationId = request.getHeader(
                CORRELATION_ID_HEADER_NAME
        );

        // Check if header correlation ID is null or empty string
        return ObjectUtils.isEmpty(headerCorrelationId)
                ? UUID.randomUUID().toString()
                : headerCorrelationId;
    }

    /**
     * Handles JSON parsing errors and sends a formatted error response.
     *
     * Sets HTTP status to 400 (Bad Request) and generates a
     * structured JSON error response with details like status, message,
     * error class, request URI, and timestamp.
     *
     * @param request The HttpServletRequest object representing the client request.
     * @param response The HttpServletResponse object used to send the error response.
     * @param ex The exception thrown, typically a JsonParseException.
     *
     * @throws IOException if writing the error response fails.
     */
    private void handleJsonParseException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        DefaultAttributeError errorResponse = DefaultAttributeError.builder()
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .message("An error occurred while parsing the JSON request data")
                .error(ex.getClass().getSimpleName())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().toString())
                .details(Collections.emptyList())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
