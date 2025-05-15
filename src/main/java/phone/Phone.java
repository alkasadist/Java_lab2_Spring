package phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Phone {
    private final PhoneCallMediator mediator;

    private String number;
    private int balance = 0;
    private State state = State.WAITING;
    private String connectedPhoneNumber;

    @Autowired
    public Phone(PhoneCallMediator mediator) {
        this.mediator = mediator;
    }

    public void setNumber(String number) {
        this.number = number;
        mediator.registerPhone(this);
    }

    public String getNumber() {
        return number;
    }

    public int getBalance() {
        return balance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getConnectedPhoneNumber() {
        return connectedPhoneNumber;
    }

    public void setConnectedPhoneNumber(String connectedPhoneNumber) {
        this.connectedPhoneNumber = connectedPhoneNumber;
    }

    public void replenishBalance(int amount) {
        this.balance += amount;

        if (this.balance >= 0 && this.state == State.BLOCKED) {
            this.state = State.WAITING;
        }
    }

    public void decreaseBalance(int amount) {
        this.balance -= amount;
    }

    public boolean call(String toNumber) {
        return mediator.makeCall(this.getNumber(), toNumber);
    }

    public boolean answer() {
        return mediator.answerCall(this);
    }

    public boolean drop() {
        return mediator.dropCall(this);
    }

    @Override
    public String toString() {
        return "PHONE: [ " +
                "number = " + number +
                ", balance = " + balance +
                ", status = " + state + " ]";
    }
}
