package cr;

public class MethodChainPublic {
    public static MethodChainPublic getInstancePublic() {
        return new MethodChainPublic();
    }

    public String helloPublic() {
        return new String( "Hello World" );
    }

    public MethodChainPublic getNewPublic() {
        return new MethodChainPublic();
    }

    public static String helloStaticPublic() {
        return new String( "Hello World" );
    }

}
