package org.projectusus.core.filerelations;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.TestServiceManager.createDescriptor;
import static org.projectusus.core.filerelations.TestServiceManager.createFileMock;

import org.junit.Test;

public class PackageCycleCalculatorTest {

    private FileRelations relations = mock( FileRelations.class );
    private PackageCycleCalculator calculator = new PackageCycleCalculator( relations );

    private Packagename packageA = new Packagename( "a" ); //$NON-NLS-1$
    private Packagename packageB = new Packagename( "b" ); //$NON-NLS-1$

    @Test
    public void simpleCycle() {
        ClassDescriptor classA = createDescriptor( createFileMock(), packageA );
        ClassDescriptor classB = createDescriptor( createFileMock(), packageB );
        when( relations.getAllDirectRelations() ).thenReturn( asList( new FileRelation( classA, classB ), new FileRelation( classB, classA ) ) );

        assertEquals( 2, calculator.countPackagesInCycles() );

        verify( relations ).getAllDirectRelations();
        verifyNoMoreInteractions( relations );
    }

}
