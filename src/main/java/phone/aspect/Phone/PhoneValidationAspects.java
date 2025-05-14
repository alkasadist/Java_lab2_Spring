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
        if (fromPhone.getState() == State.BLOCKED) {
            System.out.println("ERROR: you are blocked: your balance is " + fromPhone.getBalance());
            return false;
        }
        if (toPhone.getNumber().equals(fromPhone.getNumber())) {
            System.out.println("ERROR: you cannot call yourself.");
            return false;
        }
        if (fromPhone.getState() == State.CALLING) {
            System.out.println("ERROR: you are already calling someone.");
            return false;
        }

        return joinPoint.proceed();
    }

    @Around("execution(* phone.Phone.answer())")
    public Object validateAnswer(ProceedingJoinPoint joinPoint) throws Throwable {
        Phone fromPhone = (Phone) joinPoint.getTarget();

        if (fromPhone.getState() != State.RINGING) {
            System.out.println("ERROR: nobody is calling you.");
            return false;
        }

        return joinPoint.proceed();
    }

    @Around("execution(* phone.Phone.drop())")
    public Object validateDrop(ProceedingJoinPoint joinPoint) throws Throwable {
        Phone fromPhone = (Phone) joinPoint.getTarget();

        if (fromPhone.getState() != State.IN_CALL) {
            System.out.println("ERROR: you are not in the call.");
            return false;
        }

        return joinPoint.proceed();
    }

}
