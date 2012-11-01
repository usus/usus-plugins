package methodchaining;

public class Chain1 {
    public static Chain1 getInstance() {
        return new Chain1();
    }

    public String hello() {
        return new String( "Hello World" );
    }
}
