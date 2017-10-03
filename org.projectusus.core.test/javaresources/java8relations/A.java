package java8relations;

import java.util.function.Supplier;

public class A {

    public static Object aMethod() {
        return new Object();
    }

    public void lambdaReference() {
        // Supplier<?> supplier = () -> new B();
        new B();
    }
}
