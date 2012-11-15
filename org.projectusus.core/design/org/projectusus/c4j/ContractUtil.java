package org.projectusus.c4j;

import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class ContractUtil {

    public static String fullString( String label, WrappedTypeBinding type ) {
        return label + type.getPackagename() + " " + type.getClassname();
    }

}
