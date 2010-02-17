package org.projectusus.core.filerelations;

import static java.util.Collections.emptySet;
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
import static org.projectusus.core.filerelations.TestServiceManager.asSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith( Parameterized.class )
public class ACDCalculatorTest {

    private final int expectedCcd;
    private final Set<FileRelation> transitiveRelationsFromSource;

    @Parameters
    public static List<Object[]> data() {
        return Arrays.asList( new Object[][] { { new Integer( 1 ), emptySet() }, { new Integer( 2 ), asSet( sourceToTarget ) },
                { new Integer( 3 ), asSet( sourceToTarget, targetToAnotherTarget ) },
                { new Integer( 3 ), asSet( sourceToTarget, sourceToAnotherTarget, targetToAnotherTarget, anotherTargetToTarget, anotherTargetToSource ) }, } );
    }

    public ACDCalculatorTest( int expectedCcd, Set<FileRelation> transitiveRelationsFromSource ) {
        this.expectedCcd = expectedCcd;
        this.transitiveRelationsFromSource = transitiveRelationsFromSource;
    }

    @Test
    public void calculate() {
        FileRelations relations = mock( FileRelations.class );
        when( relations.getTransitiveRelationsFrom( source, sourceClass ) ).thenReturn( transitiveRelationsFromSource );
        int actualCcd = new ACDCalculator( relations ).getCCD( sourceDescriptor );
        assertEquals( expectedCcd, actualCcd );
        verify( relations ).getTransitiveRelationsFrom( source, sourceClass );
        verifyNoMoreInteractions( relations );
    }

}
