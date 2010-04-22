package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;

public class ClassDescriptorTest {

    private IFile file;

    @Before
    public void setup() {
        file = TestServiceManager.createFileMock();
    }

    @Test
    public void equalsIsEqual() {
        ClassDescriptor descriptor1 = TestServiceManager.createDescriptor( file );
        Object descriptor2 = TestServiceManager.createDescriptor( file );
        assertTrue( descriptor1.equals( descriptor2 ) );
    }

    @Test
    public void classIsAddedToPackageOnCreation() {
        ClassDescriptor descriptor = TestServiceManager.createDescriptor( file );
        assertTrue( descriptor.getPackagename().containsClass( descriptor ) );
    }

    @Test
    public void classIsRemovedFromPackageOnDestruction() {
        ClassDescriptor descriptor = TestServiceManager.createDescriptor( file );
        Packagename packagename = descriptor.getPackagename();
        descriptor.destroy();
        assertFalse( packagename.containsClass( descriptor ) );
        assertNull( descriptor.getFile() );
        assertNull( descriptor.getClassname() );
        assertNull( descriptor.getPackagename() );
    }
}
