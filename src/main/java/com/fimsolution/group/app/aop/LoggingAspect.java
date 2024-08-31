package com.fimsolution.group.app.aop;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Enumeration;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String REQUEST_ID_HEADER_NAME = "X-Request-ID";
    private static final String START_TIME_KEY = "startTime";

    //    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Service *)")
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void applicationPackagePointcut() {
        // Pointcut to cover all controller and service methods
    }

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        String correlationId = MDC.get(REQUEST_ID_HEADER_NAME);
        logger.info(":::Aop action: request, Method executed: {} with correlationId={}, args={}",
                joinPoint.getSignature().toShortString(), correlationId, joinPoint.getArgs());
    }

    @AfterReturning(value = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String correlationId = MDC.get(REQUEST_ID_HEADER_NAME);
        logger.info("Aop action: response, Method executed: {} with correlationId={}, result={}",
                joinPoint.getSignature().toShortString(), correlationId, result);
    }


//    @Before("applicationPackagePointcut()")
//    public void logBefore(JoinPoint joinPoint) {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes instanceof ServletRequestAttributes) {
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            String uri = request.getRequestURI();
//            String remoteIp = request.getRemoteAddr();
//
//            // Capture request data
//            StringBuilder requestData = new StringBuilder();
//            Enumeration<String> parameterNames = request.getParameterNames();
//            while (parameterNames.hasMoreElements()) {
//                String paramName = parameterNames.nextElement();
//                String paramValue = request.getParameter(paramName);
//                requestData.append(paramName).append("=").append(paramValue).append("&");
//            }
//
//            // Store start time in MDC for later use
//            MDC.put(START_TIME_KEY, String.valueOf(System.currentTimeMillis()));
//
//            // Log the request details
////            logger.info("{\"action\":\"request\", \"uri\":\"{}\", \"remoteIp\":\"{}\", \"data\":\"{}\"}",
////                    uri, remoteIp, requestData);
//
////            logger.info("{\"action\":\"request\", \"uri\":\"{}\", \"remoteIp\":\"{}\", \"data\":\"{}\"}",
////                    uri, remoteIp, Arrays.toString(joinPoint.getArgs()));
//        }
//
//        // Optionally, log method arguments
//        logger.info("{\"action\":\"request\", \"method\":\"{}\", \"args\":\"{}\"}",
//                joinPoint.getSignature().toShortString(), Arrays.toString(joinPoint.getArgs()));
//    }
//
//    @AfterReturning(value = "applicationPackagePointcut()", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        // Retrieve start time from MDC
//        String startTimeStr = MDC.get(START_TIME_KEY);
//        long execTime = 0;
//        if (startTimeStr != null) {
//            long startTime = Long.parseLong(startTimeStr);
//            execTime = System.currentTimeMillis() - startTime;
//        }
//
//        // Log the response details
//        logger.info("{\"action\":\"response\",\"execTm\":\"{}ms\",\"data\":{}}",
//                execTime, result);
//    }
}
