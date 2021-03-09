package JavaBytecodeLesson.proxy;

import JavaBytecodeLesson.models.ICalc;
import JavaBytecodeLesson.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LogCalculator implements InvocationHandler {
    private final ICalc calc;

    public LogCalculator(ICalc calc) {
        this.calc = calc;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        if (method.isAnnotationPresent(Log.class)) {
            this.printNameAndParams(method, objects);
        }

        return method.invoke(calc, objects);
    }

    private void printNameAndParams(Method method, Object[] objects) {
        String params = Arrays.stream(objects).map(Object::toString).collect(Collectors.joining(", "));
        System.out.println("Method: " + method.getName() + ", params: " + params);
    }
}
