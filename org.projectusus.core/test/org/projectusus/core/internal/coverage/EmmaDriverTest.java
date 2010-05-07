package org.projectusus.core.internal.coverage;

import org.junit.Test;

public class EmmaDriverTest {

    @Test(expected=NullPointerException.class)
    public void emmaDriverThrowsException() {
        new EmmaDriver();
    }
    
}
