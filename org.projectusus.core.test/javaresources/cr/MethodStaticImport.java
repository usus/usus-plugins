package cr;

import static cr.MethodChain.getInstance;
import static cr.MethodChainPublic.getInstancePublic;

public class MethodStaticImport {

    void testmethod() {
        getInstance();
    }
}

class MethodStaticImportPublic {

    void testmethod() {
        getInstancePublic();
    }
}
