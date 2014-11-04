package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;

public class ClassDescriptorKeyTest {

    private static final String NAME_STRING = "name";
    private static final String PACKAGENAME_STRING = "packagename";

    private Classname classname1 = new Classname( NAME_STRING, null );
    private Classname classname2 = new Classname( NAME_STRING, null );
    private Packagename packagename1 = Packagename.of( PACKAGENAME_STRING, null );
    private Packagename packagename2 = Packagename.of( PACKAGENAME_STRING, null );

    private IFile file;

    @Before
    public void setup() {
        file = mock( IFile.class );
    }

    @Test
    public void differentClassDescriptorKeysWithEqualContentsAreEqualAndNotSame() {
        ClassDescriptorKey key1a = new ClassDescriptorKey( file, classname1, packagename1 );
        ClassDescriptorKey key1b = new ClassDescriptorKey( file, classname1, packagename1 );
        ClassDescriptorKey key2 = new ClassDescriptorKey( file, classname2, packagename2 );
        assertEquals( key1a, key1b );
        assertEquals( key1a, key2 );
        assertEquals( key1b, key2 );
        assertNotSame( key1a, key1b );
        assertNotSame( key1a, key2 );
        assertNotSame( key1b, key2 );
    }

}
