package phone.aspect.PhoneCallMediator;

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
public class MediatorValidationAspect {

    @Around(
            value = "execution(* phone.PhoneCallMediator.makeCall(..)) && args(fromNumber, toNumber)",
            argNames = "joinPoint,fromNumber,toNumber"
    )
    public Object validateMakeCall(ProceedingJoinPoint joinPoint, String fromNumber, String toNumber) throws Throwable {
        PhoneCallMediator mediator = SpringContext.getBean(PhoneCallMediator.class);

        Phone toPhone = mediator.getPhone(toNumber);
        if (toPhone == null) {
            System.out.println("MEDIATOR ERROR: phone number " + toNumber + " not found.");
            return false;
        }

        if (toPhone.getState() != State.WAITING) {
            System.out.println("MEDIATOR ERROR: phone number " + toNumber + " is busy, call again later.");
            return false;
        }

        return joinPoint.proceed();
    }
}
