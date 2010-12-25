package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;

class ClassDescriptorKey {
    private final IFile file;
    private final Classname classname;
    private final Packagename packagename;

    public ClassDescriptorKey( IFile file, Classname classname, Packagename packagename ) {
        if( file == null || classname == null || packagename == null ) {
            throw new IllegalArgumentException( "Null parameters are not allowed." ); //$NON-NLS-1$
        }
        this.file = file;
        this.classname = classname;
        this.packagename = packagename;
    }

    public ClassDescriptorKey( WrappedTypeBinding type ) {
        this( type.getUnderlyingResource(), type.getClassname(), type.getPackagename() );
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof ClassDescriptorKey && equals( (ClassDescriptorKey)obj );
    }

    private boolean equals( ClassDescriptorKey other ) {
        return new EqualsBuilder().append( getFile(), other.getFile() ). //
                append( getClassname(), other.getClassname() ).append( getPackagename(), other.getPackagename() ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( getFile() ).append( getClassname() ).append( getPackagename() ).toHashCode();
    }

    @Override
    public String toString() {
        return getPackagename() + "." + getClassname() + " in " + getFile(); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public IFile getFile() {
        return file;
    }

    public Classname getClassname() {
        return classname;
    }

    public Packagename getPackagename() {
        return packagename;
    }
}
