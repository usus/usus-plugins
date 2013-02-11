package acd;

import static acd.MethodChain.getInstance;
import static acd.MethodChainPublic.getInstancePublic;

public class MethodChainStaticImport {

    void testmethod() {
        getInstance().hello();
    }
}

class MethodChainStaticImportPublic {

    void testmethod() {
        getInstancePublic().helloPublic();
    }
}
