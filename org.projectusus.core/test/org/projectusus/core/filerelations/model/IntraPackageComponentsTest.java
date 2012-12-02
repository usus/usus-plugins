package org.projectusus.core.filerelations.model;

import static org.mockito.Mockito.mock;

import org.eclipse.core.resources.IFile;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntraPackageComponentsTest {

    private Packagename pkg1 = Packagename.of( "pkg1", null );
    private Packagename pkg2 = Packagename.of( "pkg2", null );

    @Before
    public void setup() {
        ClassDescriptor.clear();
    }

    @Test
    public void emptyPackageYieldsNoComponents() {
        assertThat( new IntraPackageComponents().getComponents().size(), is( 0 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 0 ) );
    }

    @Test
    public void packageWithOneClassYieldsOneComponent() {
        addDescriptor( "pkg" );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().values().iterator().next().size(), is( 1 ) );
    }

    @Test
    public void packageWithTwoUnconnectedClassesInSamePackageYieldsTwoComponents() {
        addDescriptor( "pkg" );
        addDescriptor( "pkg" );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().values().iterator().next().size(), is( 2 ) );
    }

    @Test
    public void packageWithTwoConnectedClassesInSamePackageYieldsOneComponent() {
        ClassDescriptor parent = addDescriptor( "pkg" );
        ClassDescriptor child = addDescriptor( "pkg" );
        parent.addChild( child );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().values().iterator().next().size(), is( 1 ) );
    }

    @Test
    public void twoPackagesWithTwoUnconnectedClassesInDifferentPackagesYieldsTwoComponents() {
        addDescriptor( pkg1 );
        addDescriptor( pkg2 );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg1 ).size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg2 ).size(), is( 1 ) );
    }

    @Test
    public void twoPackagesWithTwoConnectedClassesInDifferentPackagesYieldsTwoComponents() {
        ClassDescriptor parent = addDescriptor( pkg1 );
        ClassDescriptor child = addDescriptor( pkg2 );
        parent.addChild( child );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg1 ).size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg2 ).size(), is( 1 ) );
    }

    @Test
    public void twoPackagesWithFourClassesConnectedOnlyWithinEachPackageYieldsTwoComponents() {
        ClassDescriptor p1c1 = addDescriptor( pkg1 );
        ClassDescriptor p1c2 = addDescriptor( pkg1 );
        p1c1.addChild( p1c2 );
        ClassDescriptor p2c1 = addDescriptor( pkg2 );
        ClassDescriptor p2c2 = addDescriptor( pkg2 );
        p2c1.addChild( p2c2 );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg1 ).size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg2 ).size(), is( 1 ) );
    }

    @Test
    public void twoPackagesWithFourClassesConnectedOnlyAcrossPackagesYieldsFourComponents() {
        ClassDescriptor p1c1 = addDescriptor( pkg1 );
        ClassDescriptor p1c2 = addDescriptor( pkg1 );
        ClassDescriptor p2c1 = addDescriptor( pkg2 );
        ClassDescriptor p2c2 = addDescriptor( pkg2 );
        p1c1.addChild( p2c1 );
        p1c2.addChild( p2c2 );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 4 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg1 ).size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg2 ).size(), is( 2 ) );
    }

    @Test
    public void twoPackagesWithFourClassesFullyConnectedYieldsTwoComponents() {
        ClassDescriptor p1c1 = addDescriptor( pkg1 );
        ClassDescriptor p1c2 = addDescriptor( pkg1 );
        ClassDescriptor p2c1 = addDescriptor( pkg2 );
        ClassDescriptor p2c2 = addDescriptor( pkg2 );
        p1c1.addChild( p2c1 );
        p1c1.addChild( p1c2 );
        p1c2.addChild( p2c2 );
        p2c1.addChild( p2c2 );

        assertThat( new IntraPackageComponents().getComponents().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().keySet().size(), is( 2 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg1 ).size(), is( 1 ) );
        assertThat( new IntraPackageComponents().getSetsPerPackage().get( pkg2 ).size(), is( 1 ) );
    }

    private ClassDescriptor addDescriptor( String packagename ) {
        return addDescriptor( Packagename.of( packagename, null ) );
    }

    private ClassDescriptor addDescriptor( Packagename pkg ) {
        IFile file = mock( IFile.class );
        Classname classname = mock( Classname.class );
        return ClassDescriptor.of( file, classname, pkg );
    }

    private Matcher<Integer> is( int i ) {
        return Matchers.is( Integer.valueOf( i ) );
    }

    private void assertThat( int size, Matcher<Integer> matcher ) {
        Assert.assertThat( Integer.valueOf( size ), matcher );
    }

}
