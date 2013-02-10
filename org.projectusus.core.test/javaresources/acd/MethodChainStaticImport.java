package acd;

import static acd.MethodChain.getInstance;

public class MethodChainStaticImport {

    void testmethod() {
        getInstance().hello();
    }
}

class MethodChainStaticImportPublic {

    void testmethod() {
        MethodChainPublic.getInstancePublic().helloPublic();
    }
}
