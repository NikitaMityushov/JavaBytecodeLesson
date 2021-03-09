package JavaBytecodeLesson.models;

public class Calculator implements ICalc {

    @Override
    public void add(int a, int b) {
        System.out.println(a + b);
    }

    @Override
    public void sub(int a, int b) {
        System.out.println(a - b);
    }

    @Override
    public void mult(int a, int b) {
        System.out.println(a * b);
    }

}
