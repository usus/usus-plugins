package org.projectusus.core.filerelations.internal.metrics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTargetToSource;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTargetToTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceDescriptor;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceToAnotherTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.targetToAnotherTarget;
import static org.projectusus.core.filerelations.model.TestServiceManager.asArrays;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.model.Scenario;

@RunWith( Parameterized.class )
public class ACDCalculatorTest {

    private final Scenario scenario;

    @Parameters
    public static List<Object[]> data() {
        return asArrays( new Scenario( 1 ), new Scenario( 2, sourceToTarget ), new Scenario( 3, sourceToTarget, targetToAnotherTarget ), new Scenario( 3, sourceToTarget,
                sourceToAnotherTarget, targetToAnotherTarget, anotherTargetToTarget, anotherTargetToSource ) );
    }

    public ACDCalculatorTest( Scenario scenario ) {
        this.scenario = scenario;
    }

    @Test
    public void calculate() {
        FileRelations relations = mock( FileRelations.class );
        when( relations.getTransitiveRelationsFrom( sourceDescriptor ) ).thenReturn( scenario.getInput() );
        int actualCcd = new ACDCalculator( relations ).getCCD( sourceDescriptor );
        assertEquals( scenario.getExpectedResult(), actualCcd );
        verify( relations ).getTransitiveRelationsFrom( sourceDescriptor );
        verifyNoMoreInteractions( relations );
    }

}
