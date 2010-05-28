package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;

class ClassDescriptorKey {
    public IFile file;
    public Classname classname;
    public Packagename packagename;

    public ClassDescriptorKey( IFile file, Classname classname, Packagename packagename ) {
        if( file == null || classname == null || packagename == null ) {
            throw new IllegalArgumentException( "Null parameters are not allowed." ); //$NON-NLS-1$
        }
        this.file = file;
        this.classname = classname;
        this.packagename = packagename;
    }

    public ClassDescriptorKey( BoundType type ) {
        this( type.getUnderlyingResource(), type.getClassname(), type.getPackagename() );
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof ClassDescriptorKey && equals( (ClassDescriptorKey)obj );
    }

    private boolean equals( ClassDescriptorKey other ) {
        return new EqualsBuilder().append( file, other.file ). //
                append( classname, other.classname ).append( packagename, other.packagename ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( file ).append( classname ).append( packagename ).toHashCode();
    }

    @Override
    public String toString() {
        return packagename + "." + classname + " in " + file; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
