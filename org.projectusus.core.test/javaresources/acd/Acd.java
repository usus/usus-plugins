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

// cleaned up the examples up to here

class Acd_static1 {

}

class Acd_static {
    public String getString() {
        return Acd.getString1();
    }
}

class Chain1 {
    public static Chain1 getInstance() {
        return new Chain1();
    }

    public String hello() {
        return new String( "Hello World" );
    }
}

class Chain2 {

    void testmethod() {
        Chain1.getInstance().hello();
    }
}

class Chain3 {
    public Chain3 getNew() {
        return new Chain3();
    }

    public static String hello() {
        return new String( "Hello World" );
    }
}

class Chain4 {

    void testmethod() {
        new Chain3().getNew().hello();
    }
}

class Field1 {
    public static Field1 INSTANCE = new Field1();

    public String hello = new String( "Hello World" );
}

class Field2 {

    void testmethod() {
        String x = Field1.INSTANCE.hello;
    }
}

class Field3 {
    public Field3 neww = new Field3();

    public static String hello = new String( "Hello World" );
}

class Field4 {

    void testmethod() {
        String x = new Field3().neww.hello;
    }
}

// class implements interface
// interface name; referenziert das Interface 2x
