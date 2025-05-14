package phone;

public class Tests {
    static void RunTests(PhoneFactory factory) {
        NumberNotFoundTest(factory);
        AlreadyCallingSomeoneTest(factory);
        SelfCallTest(factory);
        BlockedPhoneTest(factory);
        AlreadyCallingTest(factory);
        NoActiveCallTest(factory);
        NoCallDropTest(factory);
    }

    static void showTestResult(boolean result) {
        System.out.println(!result ? "✅ SUCCESS ✅" : "❌ FAIL ❌");
    }

    static void NumberNotFoundTest(PhoneFactory factory) {
        System.out.println("\nNumberNotFoundTest:");

        Phone phone = factory.getPhone("111");
        phone.replenishBalance(50);
        boolean result = phone.call("47348237948");

        showTestResult(result);
    }

    static void AlreadyCallingSomeoneTest(PhoneFactory factory) {
        System.out.println("\nAlreadyCallingSomeoneTest:");

        Phone phone1 = factory.getPhone("0");
        phone1.replenishBalance(100);
        Phone phone2 = factory.getPhone("1");
        Phone phone3 = factory.getPhone("2");

        phone1.call("1");
        boolean result = phone1.call("2");

        showTestResult(result);
    }

    static void SelfCallTest(PhoneFactory factory) {
        System.out.println("\nSelfCallTest:");

        Phone phone = factory.getPhone("111");
        phone.replenishBalance(100);
        boolean result = phone.call("111");

        showTestResult(result);
    }

    static void BlockedPhoneTest(PhoneFactory factory) {
        System.out.println("\nBlockedPhoneTest:");

        Phone phoneLowBalance = factory.getPhone("222");
        phoneLowBalance.replenishBalance(10);
        // you are allowed to call if you don't have enough balance, you just go into debt
        phoneLowBalance.call("111");
        phoneLowBalance.drop();
        boolean result = phoneLowBalance.call("111");

        showTestResult(result);
    }

    static void AlreadyCallingTest(PhoneFactory factory) {
        System.out.println("\nAlreadyCallingTest:");

        Phone phone1 = factory.getPhone("333");
        phone1.replenishBalance(100);
        Phone phone2 = factory.getPhone("444");
        phone2.replenishBalance(100);
        Phone phone3 = factory.getPhone("555");

        phone1.call("555");
        boolean result = phone2.call("555");

        showTestResult(result);
    }

    static void NoActiveCallTest(PhoneFactory factory) {
        System.out.println("\nNoActiveCallTest:");

        Phone phone = factory.getPhone("666");
        boolean result = phone.answer();

        showTestResult(result);
    }

    static void NoCallDropTest(PhoneFactory factory) {
        System.out.println("\nNoCallDropTest:");

        Phone phone = factory.getPhone("777");
        boolean result = phone.drop();

        showTestResult(result);
    }
}
