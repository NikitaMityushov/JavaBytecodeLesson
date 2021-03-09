package JavaBytecodeLesson.proxy;

import JavaBytecodeLesson.models.Calculator;
import JavaBytecodeLesson.models.ICalc;
import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
    }

    public static ICalc createCalculator() {
        return (ICalc) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{ICalc.class},
                new LogCalculator(new Calculator()));
    }

}
