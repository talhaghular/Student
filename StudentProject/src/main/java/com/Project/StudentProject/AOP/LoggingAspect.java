package com.Project.StudentProject.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    int reqId= ThreadLocalRandom.current().nextInt(1,100000);

    private static DateTimeFormatter FORMATER= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");

//Pointcut is Used for excecute all the classes of  service package and its all methods.
    @Pointcut("execution(* com.Project.StudentProject.Service..*(..))")
    public void serviceLogging() {}

    //Around is used for before and after both execution logging.
    @Around("serviceLogging()")
    public Object loggingMethodExecution(ProceedingJoinPoint  pjp) throws Throwable {


        //Get Service class name
        String  serviceName=pjp.getTarget().getClass().getSimpleName();

        //Get Service Method name
        String methodName=pjp.getSignature().getName();

//        log.info("Aspect for  {} {}",serviceName,methodName);

        LocalDateTime startTime=LocalDateTime.now();
        String startTimeStr=startTime.format(FORMATER);

        //Entry log
        log.info("==============Start ReqID {}=================",reqId);
        log.info("Service: {}, Method: {}",serviceName,methodName);
        log.info("startTimeStr: {}",startTimeStr);
        log.info("===============================");

        Object result=null;
        Throwable exception=null;

        try{
            result=pjp.proceed();
            return result;
        }catch (Throwable throwable1){
            exception=throwable1;
            throw exception;
        }finally {
            //Method End Logging
            LocalDateTime endTime=LocalDateTime.now();
            String endTimeStr=endTime.format(FORMATER);

            Duration duration=Duration.between(startTime,endTime);
            long durationMillis=duration.toMillis();

            log.info("===============End ReqID {}================",reqId);
            log.info("Service: {}, Method: {}",serviceName,methodName);
            if(exception !=  null){
                log.error("Status: Failed | Exception: {}",exception.getMessage());
            }
            log.info("endTimeStr: {}",endTimeStr);
            log.info("ExecutionTime: {}",durationMillis);
            log.info("===============================");

        }

    }
}
