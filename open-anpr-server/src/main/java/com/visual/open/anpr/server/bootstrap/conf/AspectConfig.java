package com.visual.open.anpr.server.bootstrap.conf;

import com.visual.open.anpr.server.utils.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AspectConfig {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(" execution(* com..PlateController.recognition(..))" )
    public void recognitionPointCut() {
    }

    @Around("recognitionPointCut()")
    public Object runTimeStatistics(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String ip = "0.0.0.0";
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            ip = RequestUtil.getRemoteHost(sra.getRequest());
        }catch (Throwable t){}

        try {
            return pjp.proceed();
        }finally {
            long costTime = System.currentTimeMillis() - startTime;
            logger.info("plate recognition cost time : {} ms, from ip: {}", costTime, ip);
        }
    }
}
