package cr;

import static cr.MethodChain.getInstance;
import static cr.MethodChainPublic.getInstancePublic;

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
