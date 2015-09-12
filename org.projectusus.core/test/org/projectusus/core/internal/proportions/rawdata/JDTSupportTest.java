package org.projectusus.core.internal.proportions.rawdata;

import org.junit.Assert;
import org.junit.Test;

public class JDTSupportTest {

    @Test
    public void getCompilationForNull() {
        Assert.assertNull( JDTSupport.getCompilationUnit( null ) );
    }

}
