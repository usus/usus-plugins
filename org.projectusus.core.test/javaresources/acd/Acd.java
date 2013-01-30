package acd;

import java.util.List;

import acd2.UnloadedClass2;

public class Acd {

    public static String getString1() {
        return "";
    }
}

class Acd2 {
    private String string;

    public String getString() {
        return string;
    }
}

class EmptyClass {
}

class Acd3Helper {
    private EmptyClass x;
}

class Acd4 {
    private Acd4Helper x;
}

class Acd4Helper {
    private Acd4 x;
}

class Acd5 {
}

class Acd5Helper {
    private static Acd5 x;
}

class Acd6<T> {

    private T string;

    public T getString() {
        return string;
    }

}

class Acd6Helper {
    private static Acd6<String> x;
}

class Acd7Helper {
    private static List<Acd> x;
}

class Acd8 {
    Helper1 h;
    Helper2 h2;
}

class Helper1 {
    Helper3 h;
    Helper4 h2;
}

class Helper2 {
    Helper5 h;
    Helper6 h2;
}

class Helper3 {
}

class Helper4 {
}

class Helper5 {
}

class Helper6 {
}

class Acd9 {
    UnloadedClass x;
}

class Acd10 {
    UnloadedClass2 x;
}

interface Intf {
}

interface Intf2 {

    Acd m();

}

class Acd11 implements Intf {
}

class Acd12 extends Acd {
}

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
