package phone.aspect.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class BeanLifecycleLogger {
//    private String logFileName = ;


    @Pointcut("execution(* phone..*(..))")
    public void allPhoneBeans() {}

    @Before("allPhoneBeans()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("[CALLED AT]    " + LocalTime.now() + " [METHOD] "+ joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "allPhoneBeans()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        System.out.println("[RETURNED AT]  " + LocalTime.now() + " [METHOD] " + joinPoint.getSignature() + "  ->  " + result);
    }
}
