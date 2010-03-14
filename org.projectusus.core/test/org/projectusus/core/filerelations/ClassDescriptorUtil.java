package org.projectusus.core.filerelations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;

public class ClassDescriptorUtil {

    public static ClassDescriptor createClassDescriptor( String name ) {
        ClassDescriptor descriptor = mock( ClassDescriptor.class );
        when( descriptor.getClassname() ).thenReturn( new Classname( name ) );
        return descriptor;
    }

}
