package phone;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PhoneFactory {
    private final ObjectFactory<Phone> phoneFactory;
    private final ConcurrentHashMap<String, Phone> phones = new ConcurrentHashMap<>();

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
