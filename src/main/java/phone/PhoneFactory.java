package phone;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
@Scope("singleton")
public class PhoneFactory {
    private final ObjectFactory<Phone> phoneFactory;
    private final HashMap<String, Phone> phones = new HashMap<>();

    public PhoneFactory(ObjectFactory<Phone> phoneFactory) {
        this.phoneFactory = phoneFactory;
    }

    public Phone getPhone(String number) {
        return phones.computeIfAbsent(number, num -> {
            Phone phone = phoneFactory.getObject();
            phone.setNumber(num);
            return phone;
        });
    }
}
