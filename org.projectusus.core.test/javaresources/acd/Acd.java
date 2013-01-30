package acd;

import java.util.List;

import acd2.UnloadedClass2;

public class Acd {
}

class Acd2 {
    private String string;

    public String getString() {
        return string;
    }
}

class Acd3 {
}

class Acd3Helper {
    private Acd3 x;
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

// class implements interface
// interface name; referenziert das Interface 2x
