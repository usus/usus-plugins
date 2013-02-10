package acd;

import static acd.FieldChain.INSTANCE;
import static acd.FieldChainPublic.INSTANCE_PUBLIC;

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
