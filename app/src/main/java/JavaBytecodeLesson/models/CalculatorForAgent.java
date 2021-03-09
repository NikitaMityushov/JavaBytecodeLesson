package JavaBytecodeLesson.models;

import JavaBytecodeLesson.annotations.Log;

public class CalculatorForAgent {
    public void add(int a, int b) {
        System.out.println(a + b);
    }

    public void sub(int a, int b) {
        System.out.println(a - b);
    }

    @Log
    public void mult(int a, int b) {
        System.out.println(a * b);
    }
}
