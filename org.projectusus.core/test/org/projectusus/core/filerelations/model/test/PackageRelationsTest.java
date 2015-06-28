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

public class PackageRelationsTest {

    private static Packagename I = Packagename.of( "I", null ); //$NON-NLS-1$
    private static Packagename II = Packagename.of( "II", null ); //$NON-NLS-1$
    private static Packagename III = Packagename.of( "III", null ); //$NON-NLS-1$

    private ClassDescriptor I_A;
    private ClassDescriptor I_B;
    private ClassDescriptor II_A;
    private ClassDescriptor II_B;
    private ClassDescriptor III_A;

    @Before
    public void setup() {
        UsusModelProvider.clear();
        I_A = createDescriptor( I );
        I_B = createDescriptor( I );
        II_A = createDescriptor( II );
        II_B = createDescriptor( II );
        III_A = createDescriptor( III );
    }

    @Test
    public void crossLinkCountIsDirected() {
        I_A.addChild( II_A );
        I_A.addChild( I_B );
        II_A.addChild( II_B );

        assertEquals( 1, getCrossLinkCount( I, II ) );
        assertEquals( 0, getCrossLinkCount( II, I ) );
    }

    @Test
    public void noCrossLinksInsidePackages() {
        I_A.addChild( I_B );

        assertEquals( 0, getCrossLinkCount( I, I ) );
    }

    @Test
    public void crossLinkCountAddsUp() {
        I_A.addChild( II_A );
        I_B.addChild( II_A );
        I_A.addChild( II_B );
        I_B.addChild( II_B );

        assertEquals( 4, getCrossLinkCount( I, II ) );
    }

    @Test
    public void referencesAmongTwoClassesCountOnce() {
        I_A.addChild( II_A );
        I_A.addChild( II_A );

        assertEquals( 1, getCrossLinkCount( I, II ) );
    }

    @Test
    public void maxLinkCount() {
        I_A.addChild( II_A );
        I_A.addChild( II_B );
        I_A.addChild( III_A );
        II_A.addChild( I_A );
        II_A.addChild( I_B );
        III_A.addChild( II_A );

        assertEquals( 2, new PackageRelations().getMaxLinkCount() );
    }

    private int getCrossLinkCount( Packagename source, Packagename target ) {
        return new PackageRelations().getCrossLinkCount( source, target );
    }

    private static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( mock( IFile.class ), new Classname( "classname1" ), packagename ); //$NON-NLS-1$
    }

}
