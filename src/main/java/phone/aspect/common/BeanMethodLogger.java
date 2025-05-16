package phone.aspect.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

@Aspect
@Component
public class BeanMethodLogger {
    private final String logFileName = System.getProperty("user.dir") + "/logs/logs.log";

    public BeanMethodLogger() {
        ensureLogFileExists();
    }

    private void ensureLogFileExists() {
        try {
            Path logPath = Paths.get(logFileName);
            Path logDir = logPath.getParent();

            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }
            if (!Files.exists(logPath)) {
                Files.createFile(logPath);
            }

        } catch (IOException e) {
            System.err.println("LOGGING ERROR: couldn't initialize log file: " + e.getMessage());
        }
    }

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