package JavaBytecodeLesson.models;

import JavaBytecodeLesson.annotations.Log;

public interface ICalc {
    @Log
    void add(int a, int b);
    @Log
    void sub(int a, int b);

    void mult(int a, int b);
}
