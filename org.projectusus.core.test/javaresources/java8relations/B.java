package java8relations;

import java.util.function.Supplier;

public class B {

    public void methodReference() {
        Supplier<Object> supplier = A::aMethod;
    }

}
