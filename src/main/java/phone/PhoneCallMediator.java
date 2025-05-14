package phone;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
public class PhoneCallMediator {
    private final Map<String, Phone> phones = new HashMap<>();

    public void registerPhone(Phone phone) {
        phones.put(phone.getNumber(), phone);
    }

    public Phone getPhone(String number) {
        return phones.get(number);
    }

    public boolean makeCall(String fromNumber, String toNumber) {
        Phone fromPhone = phones.get(fromNumber);
        Phone toPhone = phones.get(toNumber);

        fromPhone.decreaseBalance(50);
        fromPhone.setState(State.CALLING);
        toPhone.setState(State.RINGING);
        fromPhone.setConnectedPhoneNumber(toPhone.getNumber());
        toPhone.setConnectedPhoneNumber(fromPhone.getNumber());

        System.out.println(fromNumber + " is calling " + toNumber);
        return true;
    }

    public boolean answerCall(Phone caller) {
        Phone callee = phones.get(caller.getConnectedPhoneNumber());

        caller.setState(State.IN_CALL);
        callee.setState(State.IN_CALL);

        System.out.println(callee.getNumber() + " answered the " + caller.getNumber());
        return true;
    }

    public boolean dropCall(Phone caller) {
        Phone callee = phones.get(caller.getConnectedPhoneNumber());

        caller.setConnectedPhoneNumber(null);
        if (caller.getBalance() <= 0) {
            caller.setState(State.BLOCKED);
        } else {
            caller.setState(State.WAITING);
        }

        callee.setConnectedPhoneNumber(null);
        callee.setState(State.WAITING);

        System.out.println(callee.getNumber() + " dropped the " + caller.getNumber());
        return true;
    }
}
