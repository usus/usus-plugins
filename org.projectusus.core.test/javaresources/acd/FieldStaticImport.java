package acd;

import static acd.FieldChain.INSTANCE;
import static acd.FieldChainPublic.INSTANCE_PUBLIC;

public class FieldStaticImport {

    void testmethod() {
        Object hello = INSTANCE;
    }
}

class FieldStaticImportPublic {

    void testmethod() {
        Object hello = INSTANCE_PUBLIC;
    }
}
