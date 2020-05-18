package com.jamesaq12wsx.gymtime.aspect;

import com.jamesaq12wsx.gymtime.model.entity.Log;
import com.jamesaq12wsx.gymtime.service.LogService;
import com.jamesaq12wsx.gymtime.util.RequestHolder;
import com.jamesaq12wsx.gymtime.util.SecurityUtils;
import com.jamesaq12wsx.gymtime.util.StringUtils;
import com.jamesaq12wsx.gymtime.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author James Lin
 * @date 2020-05-16
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    @Autowired
    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * Point Cut
     */
    @Pointcut("@annotation(com.jamesaq12wsx.gymtime.annotation.Log)")
    public void logPointcut() {
        // TODO: Point Cut

    }

    /**
     * Log Around
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request),joinPoint, log);
        return result;
    }

    /**
     * Exception notification
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtils.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint)joinPoint, log);
    }

    public String getUsername() {
        try {
            return SecurityUtils.getCurrentUsername();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return "";
        }
    }
}