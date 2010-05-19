package org.projectusus.core.filerelations.internal.metrics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.model.TestServiceManager.asArrays;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.Scenario;

@RunWith( Parameterized.class )
public class PackageCycleCalculatorTest {

    private static Packagename I = Packagename.of( "I" ); //$NON-NLS-1$
    private static ClassDescriptor I_A = createDescriptor( I );
    private static ClassDescriptor I_B = createDescriptor( I );

    private static Packagename II = Packagename.of( "II" ); //$NON-NLS-1$
    private static ClassDescriptor II_A = createDescriptor( II );
    private static ClassDescriptor II_B = createDescriptor( II );

    private static Packagename III = Packagename.of( "III" ); //$NON-NLS-1$
    private static ClassDescriptor III_A = createDescriptor( III );

    private static Packagename IV = Packagename.of( "IV" ); //$NON-NLS-1$
    private static ClassDescriptor IV_A = createDescriptor( IV );

    private static FileRelation I_AtoI_B = FileRelation.of( I_A, I_B );
    private static FileRelation I_BtoI_A = FileRelation.of( I_B, I_A );
    private static FileRelation I_AtoII_A = FileRelation.of( I_A, II_A );
    private static FileRelation II_AtoI_A = FileRelation.of( II_A, I_A );
    private static FileRelation II_AtoIII_A = FileRelation.of( II_A, III_A );
    private static FileRelation II_BtoI_B = FileRelation.of( II_B, I_B );
    private static FileRelation III_AtoI_A = FileRelation.of( III_A, I_A );
    private static FileRelation III_AtoIV_A = FileRelation.of( III_A, IV_A );
    private static FileRelation IV_AtoIII_A = FileRelation.of( IV_A, III_A );

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
        FileRelations fileRelations = mock( FileRelations.class );
        when( fileRelations.getAllDirectRelations() ).thenReturn( scenario.getInput() );
        PackageCycleCalculator calculator = new PackageCycleCalculator( new PackageRelations( fileRelations ) );
        assertEquals( scenario.getExpectedResult(), calculator.countPackagesInCycles() );
        verify( fileRelations ).getAllDirectRelations();
        verifyNoMoreInteractions( fileRelations );
    }

}
