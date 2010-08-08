package org.projectusus.core.filerelations.internal.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.internal.metrics.PackageCycleCalculator;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class PackageCycleCalculatorTest {

    private static Packagename I = Packagename.of( "I", null ); //$NON-NLS-1$
    private static Packagename II = Packagename.of( "II", null ); //$NON-NLS-1$
    private static Packagename III = Packagename.of( "III", null ); //$NON-NLS-1$
    private static Packagename IV = Packagename.of( "IV", null ); //$NON-NLS-1$

    private ClassDescriptor I_A;
    private ClassDescriptor I_B;
    private ClassDescriptor II_A;
    private ClassDescriptor II_B;
    private ClassDescriptor III_A;
    private ClassDescriptor IV_A;

    @Before
    public void setup() {
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
        I_A.addChild( II_A );
        assertEquals( 0, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles3() {
        I_A.addChild( I_B );
        I_B.addChild( I_A );
        assertEquals( 0, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles4() {
        I_A.addChild( II_A );
        II_A.addChild( I_A );
        assertEquals( 2, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles5() {
        I_A.addChild( II_A );
        II_B.addChild( I_B );
        assertEquals( 2, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles6() {
        I_A.addChild( II_A );
        II_A.addChild( III_A );
        III_A.addChild( I_A );
        IV_A.addChild( III_A );
        assertEquals( 3, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    @Test
    public void countPackagesInCycles7() {
        I_A.addChild( II_A );
        II_A.addChild( I_A );
        III_A.addChild( IV_A );
        IV_A.addChild( III_A );
        assertEquals( 4, new PackageCycleCalculator( new PackageRelations() ).countPackagesInCycles() );
    }

    private static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( mock( IFile.class ), new Classname( "classname1" ), packagename ); //$NON-NLS-1$
    }

}
