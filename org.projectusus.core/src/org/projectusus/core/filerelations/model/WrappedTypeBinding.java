package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class WrappedTypeBinding {

    private final IFile underlyingResource;
    private final Classname classname;
    private final Packagename packagename;

    WrappedTypeBinding( IFile underlyingResource, ITypeBinding binding ) {
        this.underlyingResource = underlyingResource;
        classname = new Classname( binding.getName(), binding.getJavaElement() );
        packagename = Packagename.of( binding.getPackage().getName(), binding.getPackage().getJavaElement() );
    }

    public IFile getUnderlyingResource() {
        return underlyingResource;
    }

    public Classname getClassname() {
        return classname;
    }

    public Packagename getPackagename() {
        return packagename;
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof WrappedTypeBinding && equals( (WrappedTypeBinding)obj );
    }

    private boolean equals( WrappedTypeBinding other ) {
        return new EqualsBuilder().append( classname, other.classname ).append( packagename, other.packagename ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( classname ).append( packagename ).toHashCode();
    }
}
