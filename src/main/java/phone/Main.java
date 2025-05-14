package phone;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PhoneAppConfig.class);
        PhoneFactory factory = context.getBean(PhoneFactory.class);

        Phone phone1 = factory.getPhone("1000");
        Phone phone2 = factory.getPhone("2000");

        phone1.replenishBalance(100);
        phone2.replenishBalance(100);

        System.out.println(phone1);
        System.out.println(phone2);

        phone1.call("2000");
        System.out.println(phone1);
        System.out.println(phone2);

        phone2.answer();
        System.out.println(phone1);
        System.out.println(phone2);

        phone2.drop();
        System.out.println(phone1);
        System.out.println(phone2);
    }
}
