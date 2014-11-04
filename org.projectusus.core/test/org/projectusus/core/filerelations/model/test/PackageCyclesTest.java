package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.UsusModelProvider;

public class PackageCyclesTest {

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
        UsusModelProvider.clear();
        I_A = createDescriptor( I );
        I_B = createDescriptor( I );
        II_A = createDescriptor( II );
        II_B = createDescriptor( II );
        III_A = createDescriptor( III );
        IV_A = createDescriptor( IV );
    }

    @Test
    public void countPackagesInCycles1() {
        assertEquals( 0, packagesInCycles() );
    }

    @Test
    public void countPackagesInCycles2() {
        I_A.addChild( II_A );
        assertEquals( 0, packagesInCycles() );
    }

    @Test
    public void countPackagesInCycles3() {
        I_A.addChild( I_B );
        I_B.addChild( I_A );
        assertEquals( 0, packagesInCycles() );
    }

    @Test
    public void countPackagesInCycles4() {
        I_A.addChild( II_A );
        II_A.addChild( I_A );
        assertEquals( 2, packagesInCycles() );
    }

    @Test
    public void countPackagesInCycles5() {
        I_A.addChild( II_A );
        II_B.addChild( I_B );
        assertEquals( 2, packagesInCycles() );
    }

    @Test
    public void countPackagesInCycles6() {
        I_A.addChild( II_A );
        II_A.addChild( III_A );
        III_A.addChild( I_A );
        IV_A.addChild( III_A );
        assertEquals( 3, packagesInCycles() );
    }

    @Test
    public void countPackagesInCycles7() {
        I_A.addChild( II_A );
        II_A.addChild( I_A );
        III_A.addChild( IV_A );
        IV_A.addChild( III_A );
        assertEquals( 4, packagesInCycles() );
    }

    private int packagesInCycles() {
        return new PackageRelations().getPackageCycles().numberOfPackagesInAnyCycles();
    }

    private static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( mock( IFile.class ), new Classname( "classname1", null ), packagename ); //$NON-NLS-1$
    }

}
