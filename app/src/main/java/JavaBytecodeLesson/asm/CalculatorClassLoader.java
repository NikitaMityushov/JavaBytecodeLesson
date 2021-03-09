package JavaBytecodeLesson.asm;

public class CalculatorClassLoader extends ClassLoader {

    Class<?> defineClass(String className, byte[] originalClass) {
        String name = className.replaceAll("/", ".");
        return super.defineClass(name, originalClass, 0, originalClass.length);
    }

}
