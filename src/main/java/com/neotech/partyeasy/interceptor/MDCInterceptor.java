package com.neotech.partyeasy.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Component
public class MDCInterceptor extends OncePerRequestFilter {


    private XLogger logger = XLoggerFactory.getXLogger(getClass());

    public static final String REQUEST_ID = "Request-TraceId";
    public static final String SESSION_ID = "Session-TraceId";


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        logger.entry("Entering MDCInterceptor");
        String sessionId = httpServletRequest.getHeader("x-auth-token");
        if(sessionId!=null){
            logger.info("Retrieving the session-traceid from the session. After login");
            HttpSession newSession = httpServletRequest.getSession(false);
            if(newSession!=null){
                MDC.put(SESSION_ID, newSession.getAttribute("session-TraceId").toString());
            }else{
                logger.info("Session is not valid. generating new session traceid");
                String sessionTraceId = UUID.randomUUID().toString();
                MDC.put(SESSION_ID, sessionTraceId);
            }
        }
        else{
            logger.debug("Generating the session -traceid");
            String sessionTraceId = UUID.randomUUID().toString();
            MDC.put(SESSION_ID, sessionTraceId);
        }
        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId);

        httpServletResponse.setHeader("Request-TraceId",MDC.get(REQUEST_ID));
        httpServletResponse.setHeader("Session-TraceId",MDC.get(SESSION_ID));

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        logger.debug("Clearing MDC");
        MDC.clear();
    }
}
