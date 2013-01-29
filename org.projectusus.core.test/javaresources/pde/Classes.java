package pde;

public class Classes {

}

class ClassWithAnon {

    public void m() {
        Object o = new Object() {

        };
    }
}

class ClassWithInner {

    private class InnerClass {
    }

}

class ClassWithStaticInner {

    private static class StaticInnerClass {
    }

}

interface Interface {

}

abstract class AbstractClass {

}

enum Enum {

}

@interface AnnotationClass {

}
