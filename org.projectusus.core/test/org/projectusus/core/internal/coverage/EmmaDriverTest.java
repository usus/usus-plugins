package org.projectusus.core.internal.coverage;

import org.junit.Test;
import org.projectusus.core.internal.coverage.emmadriver.EmmaDriver;

public class EmmaDriverTest {

    @Test(expected=NullPointerException.class)
    public void emmaDriverThrowsException() {
        new EmmaDriver();
    }
    
}
