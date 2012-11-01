package methodchaining;

public class Chain3 {
    public Chain3 getNew() {
        return new Chain3();
    }

    public static String hello() {
        return new String( "Hello World" );
    }
}
