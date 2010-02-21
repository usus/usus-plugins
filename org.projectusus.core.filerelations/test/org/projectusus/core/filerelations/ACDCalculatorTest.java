package org.projectusus.core.filerelations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.SimpleTestScenario.anotherTargetToSource;
import static org.projectusus.core.filerelations.SimpleTestScenario.anotherTargetToTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.source;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceDescriptor;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceToAnotherTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.targetToAnotherTarget;
import static org.projectusus.core.filerelations.TestServiceManager.asArrays;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
        when( relations.getTransitiveRelationsFrom( source, sourceClass ) ).thenReturn( scenario.getInput() );
        int actualCcd = new ACDCalculator( relations ).getCCD( sourceDescriptor );
        assertEquals( scenario.getExpectedResult(), actualCcd );
        verify( relations ).getTransitiveRelationsFrom( source, sourceClass );
        verifyNoMoreInteractions( relations );
    }

}
