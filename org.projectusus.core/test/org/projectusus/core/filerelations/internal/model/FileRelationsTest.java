package org.projectusus.core.filerelations.internal.model;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.ANOTHER_TARGET;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.ANOTHER_TARGET_CLASS;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.SOURCE;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.SOURCE_CLASS;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.TARGET;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.TARGET_CLASS;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class FileRelationsTest {

    @Before
    public void cleanup() {
        UsusModel.clear();
    }

    @Test
    public void getAllDirectRelations() {
        ClassDescriptor sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        ClassDescriptor targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        ClassDescriptor anotherTargetDescriptor = createDescriptor( ANOTHER_TARGET, ANOTHER_TARGET_CLASS );
        sourceDescriptor.addChild( targetDescriptor );
        anotherTargetDescriptor.addChild( sourceDescriptor );
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation anotherTargetToSource = FileRelation.of( anotherTargetDescriptor, sourceDescriptor );
        assertThat( FileRelation.getAllRelations(), hasItems( sourceToTarget, anotherTargetToSource ) );
    }
}
