package JavaBytecodeLesson;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent1 {
        public static void premain(String agentArgs, Instrumentation inst) {
            System.out.println("premain");
            inst.addTransformer(new ClassFileTransformer() {
                @Override
                public byte[] transform(ClassLoader loader,
                                        String className,
                                        Class<?> classBeingRedefined,
                                        ProtectionDomain protectionDomain,
                                        byte[] classfileBuffer) {
                    //start
                    if (className.equals("JavaBytecodeLesson/models/Calculator")) {
                        return classfileBuffer;
                    }
                    return classfileBuffer;
                }
            });

    }
}
