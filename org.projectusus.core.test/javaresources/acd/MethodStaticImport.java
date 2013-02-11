package acd;

import static acd.MethodChain.getInstance;
import static acd.MethodChainPublic.getInstancePublic;

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
