package phone;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PhoneAppConfig.class);
        PhoneCallMediator mediator = context.getBean(PhoneCallMediator.class);

        Tests.RunTests(mediator);
    }
}
