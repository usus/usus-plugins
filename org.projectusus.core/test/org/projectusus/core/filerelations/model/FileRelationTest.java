package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.ANOTHER_TARGET;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.ANOTHER_TARGET_CLASS;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.SOURCE;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.SOURCE_CLASS;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.TARGET;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.TARGET_CLASS;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;

import org.junit.Before;
import org.junit.Test;

public class FileRelationTest {
    public ClassDescriptor sourceDescriptor;
    public ClassDescriptor targetDescriptor;
    public ClassDescriptor anotherTargetDescriptor;

    @Before
    public void init() {
        sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        anotherTargetDescriptor = createDescriptor( ANOTHER_TARGET, ANOTHER_TARGET_CLASS );
    }

    @Test
    public void hasSourceClass() {
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        assertTrue( sourceToTarget.hasSourceClass( SOURCE_CLASS ) );
        assertFalse( sourceToTarget.hasSourceClass( TARGET_CLASS ) );
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
