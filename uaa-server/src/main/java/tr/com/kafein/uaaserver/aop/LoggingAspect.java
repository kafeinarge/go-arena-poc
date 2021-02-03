package tr.com.kafein.uaaserver.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LoggingAspect {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springControllerPointcut() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void springServicePointcut() {
    }

    @Around("springControllerPointcut() || springServicePointcut() ")
    public Object logAroundForControllerSpringBean(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAroundInternal(joinPoint);
    }


    @AfterThrowing(pointcut = "springControllerPointcut() || springServicePointcut()", throwing = "e")
    public void logAfterThrowingForSpringBean(JoinPoint joinPoint, Throwable e) {
        logAfterThrowingInternal(joinPoint, e);
    }

    private Object logAroundInternal(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Method thisMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String className = ((MethodInvocationProceedingJoinPoint) joinPoint).getSourceLocation().getWithinType().getSimpleName();
        String methodName = thisMethod.getName();

        long startTime = System.currentTimeMillis();
        log.info("{} -> {} (ENTER)\ninput: {}\n", className, methodName, joinPoint.getArgs());

        result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long processTime = endTime - startTime;

        log.info("{} -> {} (EXIT) -> {}ms\n\ninput: {}\noutput: {}\n", className, methodName, processTime, joinPoint.getArgs(), result);
        return result;
    }

    public void logAfterThrowingInternal(JoinPoint joinPoint, Throwable e) {
        Method thisMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String className = ((MethodInvocationProceedingJoinPoint) joinPoint).getSourceLocation().getWithinType().getName();
        String methodName = thisMethod.getName();

        log.error("{} -> {}\n\ninput: {}\noutput: {}", className, methodName, joinPoint.getArgs(), e.getMessage());
    }
}
