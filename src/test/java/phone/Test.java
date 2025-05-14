package phone;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PhoneAppConfig.class);
        PhoneFactory factory = context.getBean(PhoneFactory.class);

        Tests.RunTests(factory);
    }
}
