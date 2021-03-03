package JavaBytecodeLesson;

import java.lang.reflect.Proxy;

public class App {
    public static void main(String[] args) {
        ICalc calculator = (ICalc) Proxy.newProxyInstance(Calculator.class.getClassLoader(),
                Calculator.class.getInterfaces(),
                new LogCalculator(new Calculator()));

        calculator.add(2, 3);
    }
}
