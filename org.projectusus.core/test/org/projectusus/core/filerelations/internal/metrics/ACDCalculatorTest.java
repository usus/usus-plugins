package org.projectusus.core.filerelations.internal.metrics;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTargetClass;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.source;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.target;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.targetClass;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class ACDCalculatorTest {

    private ClassDescriptor sourceDescriptor;
    private ClassDescriptor targetDescriptor;
    private ClassDescriptor anotherTargetDescriptor;

    @Before
    public void cleanup() {
        UsusModel.clear();
        sourceDescriptor = createDescriptor( source, sourceClass );
        targetDescriptor = createDescriptor( target, targetClass );
        anotherTargetDescriptor = createDescriptor( anotherTarget, anotherTargetClass );
    }

    @Test
    public void calculateNoRelations() {
        assertEquals( 1, sourceDescriptor.getCCD() );
    }

    @Test
    public void calculateOneRelation() {
        FileRelation.of( sourceDescriptor, targetDescriptor );
        assertEquals( 2, sourceDescriptor.getCCD() );
    }

    @Test
    public void calculateTwoRelations() {
        FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation.of( targetDescriptor, anotherTargetDescriptor );
        assertEquals( 3, sourceDescriptor.getCCD() );
    }

    @Test
    public void calculateManyRelations() {
        FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation.of( sourceDescriptor, anotherTargetDescriptor );
        FileRelation.of( targetDescriptor, anotherTargetDescriptor );
        FileRelation.of( anotherTargetDescriptor, targetDescriptor );
        FileRelation.of( anotherTargetDescriptor, sourceDescriptor );
        assertEquals( 3, sourceDescriptor.getCCD() );
    }
}
