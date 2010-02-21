package org.projectusus.core.filerelations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.TestServiceManager.asArrays;
import static org.projectusus.core.filerelations.TestServiceManager.createDescriptor;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith( Parameterized.class )
public class PackageCycleCalculatorTest {

    private static Packagename I = new Packagename( "I" ); //$NON-NLS-1$
    private static ClassDescriptor I_A = createDescriptor( I );
    private static ClassDescriptor I_B = createDescriptor( I );

    private static Packagename II = new Packagename( "II" ); //$NON-NLS-1$
    private static ClassDescriptor II_A = createDescriptor( II );
    private static ClassDescriptor II_B = createDescriptor( II );

    private static Packagename III = new Packagename( "III" ); //$NON-NLS-1$
    private static ClassDescriptor III_A = createDescriptor( III );

    private static Packagename IV = new Packagename( "IV" ); //$NON-NLS-1$
    private static ClassDescriptor IV_A = createDescriptor( IV );

    private static FileRelation I_AtoI_B = new FileRelation( I_A, I_B );
    private static FileRelation I_BtoI_A = new FileRelation( I_B, I_A );
    private static FileRelation I_AtoII_A = new FileRelation( I_A, II_A );
    private static FileRelation II_AtoI_A = new FileRelation( II_A, I_A );
    private static FileRelation II_AtoIII_A = new FileRelation( II_A, III_A );
    private static FileRelation II_BtoI_B = new FileRelation( II_B, I_B );
    private static FileRelation III_AtoI_A = new FileRelation( III_A, I_A );
    private static FileRelation III_AtoIV_A = new FileRelation( III_A, IV_A );
    private static FileRelation IV_AtoIII_A = new FileRelation( IV_A, III_A );

    private FileRelations relations = mock( FileRelations.class );
    private PackageCycleCalculator calculator = new PackageCycleCalculator( relations );
    private final Scenario scenario;

    @Parameters
    public static List<Object[]> parameters() {
        return asArrays( new Scenario( 0 ), new Scenario( 0, I_AtoII_A ), new Scenario( 0, I_AtoI_B, I_BtoI_A ), new Scenario( 2, I_AtoII_A, II_AtoI_A ), new Scenario( 2,
                I_AtoII_A, II_BtoI_B ), new Scenario( 3, I_AtoII_A, II_AtoIII_A, III_AtoI_A, IV_AtoIII_A ), new Scenario( 4, I_AtoII_A, II_AtoI_A, III_AtoIV_A, IV_AtoIII_A ) );
    }

    public PackageCycleCalculatorTest( Scenario scenario ) {
        this.scenario = scenario;
    }

    @Test
    public void countPackagesInCycles() {
        when( relations.getAllDirectRelations() ).thenReturn( scenario.getInput() );
        assertEquals( scenario.getExpectedResult(), calculator.countPackagesInCycles() );
        verify( relations ).getAllDirectRelations();
        verifyNoMoreInteractions( relations );
    }

}
