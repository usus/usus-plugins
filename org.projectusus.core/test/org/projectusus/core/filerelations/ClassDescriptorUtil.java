package org.projectusus.core.filerelations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;

public class ClassDescriptorUtil {

    public static ClassDescriptor createClassDescriptor( String name ) {
        ClassDescriptor descriptor = mock( ClassDescriptor.class );
        when( descriptor.getClassname() ).thenReturn( new Classname( name ) );
        when( descriptor.getPackagename() ).thenReturn( Packagename.of( name ) );
        return descriptor;
    }

}
