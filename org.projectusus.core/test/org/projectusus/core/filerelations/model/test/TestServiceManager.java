package org.projectusus.core.filerelations.model.test;

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

    public static IFile createFileMock() {
        return mock( IFile.class );
    }

    public static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( createFileMock(), createClassname(), packagename );
    }

    public static ClassDescriptor createDescriptor( IFile file, Classname clazz ) {
        return createDescriptor( file, clazz, Packagename.of( "packagename1", null ) ); //$NON-NLS-1$
    }

    public static ClassDescriptor createDescriptor( IFile file, Packagename packagename ) {
        return ClassDescriptor.of( file, createClassname(), packagename );
    }

    public static Classname createClassname() {
        return new Classname( "classname1" ); //$NON-NLS-1$
    }

    public static ClassDescriptor createDescriptor( IFile file, Classname clazz, Packagename packagename ) {
        return ClassDescriptor.of( file, clazz, packagename );
    }

    public static ClassDescriptor createDescriptor( IFile file ) {
        return createDescriptor( file, createClassname() );
    }

    public static List<Object[]> asArrays( Object... items ) {
        List<Object[]> result = new ArrayList<Object[]>();
        for( Object item : items ) {
            result.add( new Object[] { item } );
        }
        return result;
    }

}
