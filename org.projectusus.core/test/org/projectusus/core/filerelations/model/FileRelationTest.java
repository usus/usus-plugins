package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.targetClass;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;

import org.junit.Test;

public class FileRelationTest {

    @Test
    public void hasSourceClass() {
        assertTrue( sourceToTarget.hasSourceClass( sourceClass ) );
        assertFalse( sourceToTarget.hasSourceClass( targetClass ) );
    }

    @Test
    public void isCrossPackage() {
        ClassDescriptor first = createDescriptor( new Packagename( "x" ) ); //$NON-NLS-1$
        ClassDescriptor second = createDescriptor( new Packagename( "y" ) ); //$NON-NLS-1$
        assertTrue( new FileRelation( first, second ).isCrossPackage() );
        assertTrue( new FileRelation( second, first ).isCrossPackage() );
        assertFalse( new FileRelation( first, first ).isCrossPackage() );
    }
}
