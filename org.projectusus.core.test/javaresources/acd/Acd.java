package acd;

import java.util.List;

import acd2.UnloadedClass2;

public class Acd {

    public static String getString1() {
        return "";
    }
}

class ClassWithStrings {
    private String string;

    public String getString() {
        return string;
    }
}

class EmptyClass {
}

class ToEmptyClass {
    private EmptyClass x;
}

class ClassWithGeneric<T> {

    private T string;

    public T getString() {
        return string;
    }
}

class ToClassWithGeneric {
    private static ClassWithGeneric<String> x;
}

class ToClassAsGenericArgument {
    private static List<Acd> x;
}

class ToTwoClasses {
    EmptyClass h1;
    Acd h2;
}

class ToUnloadedClass {
    UnloadedClass x;
}

class ToUnloadedClassInDifferentPackage {
    UnloadedClass2 x;
}

interface EmptyInterface {
}

interface InterfaceToClass {

    Acd m();

}

class ImplementingClass implements EmptyInterface {
}

class ExtendingClass extends Acd {
}

class InvokingStaticMethod {
    public String getString() {
        return Acd.getString1();
    }
}

class MethodChain {
    public static MethodChain getInstance() {
        return new MethodChain();
    }

    public String hello() {
        return new String( "Hello World" );
    }

    public MethodChain getNew() {
        return new MethodChain();
    }

    public static String helloStatic() {
        return new String( "Hello World" );
    }
}

class MethodChain_StaticNonstatic {

    void testmethod() {
        MethodChain.getInstance().hello();
    }
}

class MethodChain_NonstaticStatic {

    void testmethod() {
        new MethodChain().getNew().hello();
    }
}

class FieldChain {
    public static FieldChain INSTANCE = new FieldChain();

    public String hello = new String( "Hello World" );

    public FieldChain neww = new FieldChain();

    public static String helloStatic = new String( "Hello World" );
}

class FieldChain_StaticNonstatic {

    void testmethod() {
        String x = FieldChain.INSTANCE.hello;
    }
}

class FieldChain_NonstaticStatic {

    void testmethod() {
        String x = new FieldChain().neww.helloStatic;
    }
}

class FieldChain_StaticNonstaticPublic {

    void testmethod() {
        String x = FieldChainPublic.INSTANCE_PUBLIC.hello_public;
    }
}

class FieldChain_NonstaticStaticPublic {

    void testmethod() {
        String x = new FieldChainPublic().neww_public.helloStatic_public;
    }
}

// cleaned up the examples up to here

// class implements interface
// interface name; referenziert das Interface 2x
