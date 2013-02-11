package cr;

import static cr.FieldChain.INSTANCE;
import static cr.FieldChainPublic.INSTANCE_PUBLIC;

public class FieldChainStaticImport {

    void testmethod() {
        String hello = INSTANCE.hello;
    }
}

class FieldChainStaticImportPublic {

    void testmethod() {
        String hello = INSTANCE_PUBLIC.hello_public;
    }
}
