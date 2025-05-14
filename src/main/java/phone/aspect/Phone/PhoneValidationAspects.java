package phone.aspect.Phone;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import phone.Phone;
import phone.PhoneCallMediator;
import phone.SpringContext;
import phone.State;

@Aspect
@Component
public class PhoneValidationAspects {

    @Around("execution(* phone.Phone.call(String))")
    public Object validateCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String toNumber = (String) joinPoint.getArgs()[0];
        PhoneCallMediator mediator = SpringContext.getBean(PhoneCallMediator.class);

        Phone toPhone = mediator.getPhone(toNumber);
        Phone fromPhone = (Phone) joinPoint.getTarget();

        if (toPhone == null) {
            System.out.println("ERROR: phone number " + toNumber + " not found.");
            return false;
        }
        if (fromPhone.getState() == State.CALLING) {
            System.out.println("ERROR: You are already calling someone.");
            return false;
        }
        if (fromPhone.getBalance() <= 0) {
            System.out.println("ERROR: Insufficient balance.");
            return false;
        }

        return joinPoint.proceed();
    }
}
