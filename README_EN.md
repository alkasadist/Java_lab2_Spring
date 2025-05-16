**Available languages:**
- 🇷🇺 [Русский](README.md)
- 🇺🇸 [English](README_EN.md)


# 📚 Lab Work №2:
This includes the migration of [Lab Work №1](https://github.com/alkasadist/Java_lab1_Patterns)
to **Spring**, as well as the integration of **IoC**, **DI**, **AOP**, and logging.


## Task:
You need to improve the system implemented in Lab №1 using your knowledge of Spring, DI, IoC, and AOP.
Key/significant objects in your system (such as factories) should be implemented as
Spring Beans (singletons and prototypes). They should be used in code via **DI**.
Think carefully about what exactly makes sense to turn into a Spring Bean — don’t make all classes/objects into beans.
Use **AOP** to implement logging of Spring Beans.


## 📖 Explanation:
(system tests are located [here](src/test/java/phone/Test.java))

### The following components were converted to Spring Beans:
- ```Phone``` (Prototype):  
  contains the phone's fields and interface.
- ```PhoneCallMediator``` (Singleton):  
  the only controller responsible for managing calls between ```Phone``` instances.
  Injected into each ```Phone``` using **DI**.
- ```PhoneFactory``` (Singleton):  
  a factory implementing the creation of Multiton ```Phone``` instances.

### The following aspects were added:
- ```PhoneValidationAspects``` – validation aspect for the ```Phone``` class.
- ```MediatorValidationAspects``` – validation aspect for the ```PhoneCallMediator``` class.
- ```BeanMethodLogger``` – logs **method call time** and **completion result** of
  all invoked methods to the file ```logs/logs.log``` (the file is created automatically at runtime).
