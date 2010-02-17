package org.projectusus.core.filerelations;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.junit.Test;

public class ClassDescriptorTest {

    @Test
    public void equalsIsEqual() {
        IFile file = TestServiceManager.createFileMock();
        ClassDescriptor descriptor1 = TestServiceManager.createDescriptor( file );
        Object descriptor2 = TestServiceManager.createDescriptor( file );
        assertTrue( descriptor1.equals( descriptor2 ) );
    }
}
