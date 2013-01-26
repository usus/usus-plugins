package publicfields;

public class PublicFields {

}

class EmptyClass {
}

class OneOfEachKind {

    private int a;
    protected int b;
    int c;
    public int d;
    public static int e;
    public final int f = 0;
    public static final int g = 0;

}

class OneOfEachKindTwice {

    private int a;
    protected int b;
    int c;
    public int d;
    public static int e;
    public final int f = 0;
    public static final int g = 0;

    public class AnotherClassWithFields {
        private int a;
        protected int b;
        int c;
        public int d;
        public final int f = 0;
        public static final int g = 0;
    }

}

class OneOfEachKindTwoClasses {

    private int a;
    protected int b;
    int c;
    public int d;
    public static int e;
    public final int f = 0;
    public static final int g = 0;

    public class AnotherClassWithoutFields {
    }

}

class OnePublicField {
    public int i = 0;
}

class OnlyPublicStaticFinal {

    private int a;
    protected int b;
    int c;
    public static final int d = 0;

}
