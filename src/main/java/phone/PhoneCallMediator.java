package phone;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
public class PhoneCallMediator {
    private final Map<String, PhoneProxy> phones = new HashMap<>();

    public void registerPhone(PhoneProxy phone) {
        phones.put(phone.getNumber(), phone);
    }

    public PhoneProxy getPhone(String number) {
        return phones.get(number);
    }

    public boolean makeCall(String fromNumber, String toNumber) {
        PhoneProxy fromPhone = phones.get(fromNumber);
        PhoneProxy toPhone = phones.get(toNumber);

        fromPhone.decreaseBalance(50);
        fromPhone.setState(State.CALLING);
        toPhone.setState(State.RINGING);
        fromPhone.setConnectedPhoneNumber(toPhone.getNumber());
        toPhone.setConnectedPhoneNumber(fromPhone.getNumber());

        System.out.println(fromNumber + " is calling " + toNumber);
        return true;
    }

    public boolean answerCall(PhoneProxy caller) {
        PhoneProxy callee = phones.get(caller.getConnectedPhoneNumber());

        caller.setState(State.IN_CALL);
        callee.setState(State.IN_CALL);

        System.out.println(callee.getNumber() + " answered the " + caller.getNumber());
        return true;
    }

    public boolean dropCall(PhoneProxy caller) {
        PhoneProxy callee = phones.get(caller.getConnectedPhoneNumber());

        caller.setConnectedPhoneNumber(null);
        caller.setState(State.WAITING);
        callee.setConnectedPhoneNumber(null);
        callee.setState(State.WAITING);

        System.out.println(callee.getNumber() + " dropped the " + caller.getNumber());
        return true;
    }
}
