package org.projectusus.core.filerelations.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;

public class ClassDescriptor {

    private ClassDescriptorKey key;

    private static final Map<ClassDescriptorKey, ClassDescriptor> classes = new HashMap<ClassDescriptorKey, ClassDescriptor>();

    public static Set<ClassDescriptor> getAll() {
        return new HashSet<ClassDescriptor>( classes.values() );
    }

    public static ClassDescriptor of( BoundType type ) {
        return ClassDescriptor.of( new ClassDescriptorKey( type.getUnderlyingResource(), type.getClassname(), type.getPackagename() ) );
    }

    public static ClassDescriptor of( IFile file, Classname classname, Packagename packagename ) {
        return ClassDescriptor.of( new ClassDescriptorKey( file, classname, packagename ) );
    }

    private static ClassDescriptor of( ClassDescriptorKey key ) {
        if( classes.containsKey( key ) ) {
            return classes.get( key );
        }
        ClassDescriptor newClassDescriptor = new ClassDescriptor( key );
        classes.put( key, newClassDescriptor );
        return newClassDescriptor;
    }

    public static void removeAllClassesIn( IFile file ) {
        Set<ClassDescriptorKey> keys = new HashSet<ClassDescriptorKey>( classes.keySet() );
        for( ClassDescriptorKey key : keys ) {
            if( key.file.equals( file ) ) {
                ClassDescriptor descriptor = classes.remove( key );
                descriptor.removeFromPackage();
            }
        }
    }

    private ClassDescriptor( ClassDescriptorKey key ) {
        this.key = key;
        key.packagename.addClass( this );
    }

    private void removeFromPackage() {
        key.packagename.removeClass( this );
    }

    public IFile getFile() {
        return key.file;
    }

    public Classname getClassname() {
        return key.classname;
    }

    public Packagename getPackagename() {
        return key.packagename;
    }

    @Override
    public boolean equals( Object object ) {
        return object instanceof ClassDescriptor && equals( (ClassDescriptor)object );
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(). //
                append( key.file ). //
                append( key.classname ). //
                append( key.packagename ). //
                toHashCode();
    }

    private boolean equals( ClassDescriptor other ) {
        return new EqualsBuilder(). //
                append( key.file, other.key.file ). //
                append( key.classname, other.key.classname ). //
                append( key.packagename, other.key.packagename ). //
                isEquals();
    }

    @Override
    public String toString() {
        return key.packagename + "." + key.classname + "[" + key.file + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

}
