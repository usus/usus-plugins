package trainwreck;

public class TrainWreck {

    private TrainWreck c;

    public void empty() {

    }

    public void oneMethodCall() {
        a();
    }

    public void threeMethods() {
        a().b().x();
    }

    public void twoStatements() {
        a().b();
        x().y();
    }

    public void twoMethodsWithArguments() {
        a( x().y() ).b( z().w() );
    }

    public void assignment() {
        a().b().c = x().y();
    }

    // ///////////////////////////////////////////////////////
    // Helper methods to make this class compile

    private TrainWreck a() {
        return null;
    }

    private TrainWreck a( TrainWreck x ) {
        return null;
    }

    private TrainWreck b() {
        return null;
    }

    private TrainWreck b( TrainWreck x ) {
        return null;
    }

    private TrainWreck w() {
        return null;
    }

    private TrainWreck x() {
        return null;
    }

    private TrainWreck y() {
        return null;
    }

    private TrainWreck z() {
        return null;
    }

}
