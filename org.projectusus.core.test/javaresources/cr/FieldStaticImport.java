package cr;

import static cr.FieldChain.INSTANCE;
import static cr.FieldChainPublic.INSTANCE_PUBLIC;

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
