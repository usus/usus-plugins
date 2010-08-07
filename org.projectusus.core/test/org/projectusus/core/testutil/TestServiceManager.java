package org.projectusus.core.testutil;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;

public class TestServiceManager {

    public static <T> Set<T> asSet( T... items ) {
        return new HashSet<T>( Arrays.asList( items ) );
    }

    public static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( mock( IFile.class ), new Classname( "classname1" ), packagename ); //$NON-NLS-1$
    }

    public static ClassDescriptor createDescriptor( IFile file ) {
        return ClassDescriptor.of( file, new Classname( "classname1" ), Packagename.of( "packagename1", null ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static List<Object[]> asArrays( Object... items ) {
        List<Object[]> result = new ArrayList<Object[]>();
        for( Object item : items ) {
            result.add( new Object[] { item } );
        }
        return result;
    }

}
