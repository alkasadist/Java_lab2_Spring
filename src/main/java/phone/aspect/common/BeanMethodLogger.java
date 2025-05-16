package phone.aspect.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

@Aspect
@Component
public class BeanMethodLogger {
    private final String logFileName = System.getProperty("user.dir") + "/logs/logs.log";

    @Pointcut("execution(* phone..*(..))")
    public void allPhoneBeans() {}

    @Before("allPhoneBeans()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String logText = "[CALLED AT]    " + LocalTime.now() + " [METHOD] "
                            + joinPoint.getSignature() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logText);
        } catch (IOException e) {
            System.err.println("LOGGING ERROR: couldn't write to the log file: " + e.getMessage());
        }
    }

    @AfterReturning(pointcut = "allPhoneBeans()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        String logText = "[RETURNED AT]  " + LocalTime.now() + " [METHOD] "
                            + joinPoint.getSignature() + "  ->  " + result + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logText);
        } catch (IOException e) {
            System.err.println("LOGGING ERROR: couldn't write to the log file: " + e.getMessage());
        }
    }
}
