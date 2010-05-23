package org.projectusus.core.filerelations.internal.metrics;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class PackageCycleCalculatorTest {

    private static Packagename I = Packagename.of( "I" ); //$NON-NLS-1$
    private static Packagename II = Packagename.of( "II" ); //$NON-NLS-1$
    private static Packagename III = Packagename.of( "III" ); //$NON-NLS-1$
    private static Packagename IV = Packagename.of( "IV" ); //$NON-NLS-1$

    private ClassDescriptor I_A;
    private ClassDescriptor I_B;
    private ClassDescriptor II_A;
    private ClassDescriptor II_B;
    private ClassDescriptor III_A;
    private ClassDescriptor IV_A;

    @Before
    public void cleanup() {
        UsusModel.clear();
        I_A = createDescriptor( I );
        I_B = createDescriptor( I );
        II_A = createDescriptor( II );
        II_B = createDescriptor( II );
        III_A = createDescriptor( III );
        IV_A = createDescriptor( IV );
    }

    @Test
    public void countPackagesInCycles1() {
        assertEquals( 0, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles2() {
        FileRelation.of( I_A, II_A );
        assertEquals( 0, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles3() {
        FileRelation.of( I_A, I_B );
        FileRelation.of( I_B, I_A );
        assertEquals( 0, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles4() {
        FileRelation.of( I_A, II_A );
        FileRelation.of( II_A, I_A );
        assertEquals( 2, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles5() {
        FileRelation.of( I_A, II_A );
        FileRelation.of( II_B, I_B );
        assertEquals( 2, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles6() {
        FileRelation.of( I_A, II_A );
        FileRelation.of( II_A, III_A );
        FileRelation.of( III_A, I_A );
        FileRelation.of( IV_A, III_A );
        assertEquals( 3, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles7() {
        FileRelation.of( I_A, II_A );
        FileRelation.of( II_A, I_A );
        FileRelation.of( III_A, IV_A );
        FileRelation.of( IV_A, III_A );
        assertEquals( 4, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }
}
