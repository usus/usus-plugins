package org.projectusus.core.filerelations.model.classdescriptortest;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;

public class ClassDescriptorTest {

    private static final String NAME_STRING = "name";
    private static final String PACKAGENAME_STRING = "packagename";

    private Classname classname1 = new Classname( NAME_STRING );
    private Classname classname2 = new Classname( NAME_STRING );
    private Packagename packagename1 = Packagename.of( PACKAGENAME_STRING, null );
    private Packagename packagename2 = Packagename.of( PACKAGENAME_STRING, null );

    private IFile file;

    @Before
    public void setup() {
        file = mock( IFile.class );
    }

    @Test
    public void equalsIsEqual() {
        ClassDescriptor descriptor1 = createDescriptor( file );
        Object descriptor2 = createDescriptor( file );
        assertTrue( descriptor1.equals( descriptor2 ) );
        assertSame( descriptor1, descriptor2 );
    }

    @Test
    public void differentClassDescriptorsWithEqualContentsAreEqualAndSame() {
        ClassDescriptor descriptor1a = ClassDescriptor.of( file, classname1, packagename1 );
        ClassDescriptor descriptor1b = ClassDescriptor.of( file, classname1, packagename1 );
        ClassDescriptor descriptor2 = ClassDescriptor.of( file, classname2, packagename2 );
        assertEquals( descriptor1a, descriptor2 );
        assertEquals( descriptor1a, descriptor1b );
        assertEquals( descriptor1b, descriptor2 );
        assertSame( descriptor1a, descriptor2 );
        assertSame( descriptor1a, descriptor1b );
        assertSame( descriptor1b, descriptor2 );
    }

    @Test
    public void classIsAddedToPackageOnCreation() {
        ClassDescriptor descriptor = createDescriptor( file );
        assertTrue( descriptor.getPackagename().containsClass( descriptor ) );
    }

    @Test
    public void classIsRemovedFromPackageOnDestruction() {
        ClassDescriptor descriptor = createDescriptor( file );
        Packagename packagename = descriptor.getPackagename();
        descriptor.removeFromPool();
        assertFalse( packagename.containsClass( descriptor ) );
    }

    @Test
    public void isCrossPackage() {
        ClassDescriptor first = createDescriptor( Packagename.of( "x", null ) ); //$NON-NLS-1$
        ClassDescriptor second = createDescriptor( Packagename.of( "y", null ) ); //$NON-NLS-1$
        first.addChild( second );
        assertThat( first.getChildrenInOtherPackages(), hasItems( second ) );
    }

    @Test
    public void classIsNotAddedToItsChildren() {
        ClassDescriptor descriptor = ClassDescriptor.of( mock( IFile.class ), new Classname( "classname" ), Packagename.of( NAME_STRING, null ) ); //$NON-NLS-1$ 
        assertEquals( 0, descriptor.getChildren().size() );
        descriptor.addChild( descriptor );
        assertEquals( 0, descriptor.getChildren().size() );
    }

    private static ClassDescriptor createDescriptor( IFile file ) {
        return ClassDescriptor.of( file, new Classname( "classname1" ), Packagename.of( "packagename1", null ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( mock( IFile.class ), new Classname( "classname1" ), packagename ); //$NON-NLS-1$
    }

}
