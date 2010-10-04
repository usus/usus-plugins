package publicfields;

public class OneOfEachKindTwice {

    private int a;
    protected int b;
    int c;
    public int d;
    public static int e;
    public final int f = 0;
    public static final int g = 0;

    public class anotherClass {
        private int a;
        protected int b;
        int c;
        public int d;
        public final int f = 0;
        public static final int g = 0;
    }

}
