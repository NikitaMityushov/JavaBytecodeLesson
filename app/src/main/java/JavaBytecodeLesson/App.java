package JavaBytecodeLesson;
import JavaBytecodeLesson.models.Calculator;
import JavaBytecodeLesson.models.ICalc;
import JavaBytecodeLesson.proxy.Ioc;
import org.checkerframework.checker.units.qual.C;

/*
    java -javaagent:app.jar -jar app.jar
 */

public class App {
    public static void main(String[] args) {
        /*
        ICalc calculator = Ioc.createCalculator();
        calculator.sub(2, 3);

         */

        Calculator calculator = new Calculator();

        calculator.mult(2,3);
    }
}
