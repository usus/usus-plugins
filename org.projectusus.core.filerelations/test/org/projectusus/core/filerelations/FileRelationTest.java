package org.projectusus.core.filerelations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.targetClass;

import org.junit.Test;

public class FileRelationTest {

    @Test
    public void hasSourceClass() {
        assertTrue( sourceToTarget.hasSourceClass( sourceClass ) );
        assertFalse( sourceToTarget.hasSourceClass( targetClass ) );
    }
}
