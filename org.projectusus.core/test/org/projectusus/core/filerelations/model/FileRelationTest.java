package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTargetDescriptor;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceDescriptor;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.targetClass;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.targetDescriptor;
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
        ClassDescriptor first = createDescriptor( Packagename.of( "x" ) ); //$NON-NLS-1$
        ClassDescriptor second = createDescriptor( Packagename.of( "y" ) ); //$NON-NLS-1$
        assertTrue( FileRelation.of( first, second ).isCrossPackage() );
        assertTrue( FileRelation.of( second, first ).isCrossPackage() );
        assertFalse( FileRelation.of( first, first ).isCrossPackage() );
    }

    @Test
    public void objectIdentities() {
        FileRelation relation1 = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation relation2 = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation relation3 = FileRelation.of( sourceDescriptor, anotherTargetDescriptor );
        assertSame( relation1, relation2 );
        assertNotSame( relation1, relation3 );
    }
}
