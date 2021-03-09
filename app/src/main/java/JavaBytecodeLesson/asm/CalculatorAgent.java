package JavaBytecodeLesson.asm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

import JavaBytecodeLesson.annotations.Log;
import org.objectweb.asm.*;


public class CalculatorAgent {

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
                if (className.equals("JavaBytecodeLesson/models/CalculatorForAgent")) {
                    Set<String> annotatedMethods = new HashSet<>();
                    CalculatorClassLoader cld = new CalculatorClassLoader();
                    Class<?> clazz = cld.defineClass(className, classfileBuffer);
                    Method[] methods = clazz.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Log.class)) {
                            annotatedMethods.add(method.getName());
                        }
                    }

                    if (!annotatedMethods.isEmpty()) {
                        return addProxyMethod(classfileBuffer, annotatedMethods, className);
                    }
                }
                return classfileBuffer;
            }
        });
    }

    private static byte[] addProxyMethod(byte[] originalClass, Set<String> annotatedMethods, String className) {
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

                if (annotatedMethods.contains(name)) {
                    return super.visitMethod(access, name + "Proxied", descriptor, signature, exceptions);
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };

        cr.accept(cv, Opcodes.ASM5);

        Handle handle = new Handle(
                Opcodes.H_INVOKESTATIC,
                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Object[].class).toMethodDescriptorString(),
                false);

        for (String method : annotatedMethods) {
            // create public void multProxied(int a, int b) method
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, method, "(Ljava/lang/String;)V", null, null);

            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitInvokeDynamicInsn("makeConcatWithConstants","(Ljava/lang/String;)Ljava/lang/String;", handle, "logged param: [\u0001]");

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, method + "Proxied", "(Ljava/lang/String;)V", false);

            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
            // end of
        }

        return cw.toByteArray(); // byte[] finalClass = cw.toByteArray();
    }
}
