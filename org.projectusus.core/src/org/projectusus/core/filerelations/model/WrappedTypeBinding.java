package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.projectusus.core.project2.UsusProjectSupport;

public class WrappedTypeBinding {

    private static final String JAVA = "java"; //$NON-NLS-1$

    private boolean isValid = false;
    private ITypeBinding binding;
    private IFile underlyingResource;
    private Classname classname;
    private Packagename packagename;

    public WrappedTypeBinding( ITypeBinding typeBinding ) {
        this.binding = typeBinding;
        if( binding == null || binding.isPrimitive() ) {
            return;
        }
        binding = binding.getErasure();
        if( binding == null || binding.isPrimitive() ) {
            return;
        }
        if( binding.isTypeVariable() || binding.isCapture() || binding.isWildcardType() ) {
            return;
        }
        try {
            underlyingResource = (IFile)binding.getJavaElement().getUnderlyingResource();
        } catch( Throwable t ) {
            return;
        }
        if( underlyingResource == null || !underlyingResource.getFileExtension().equals( JAVA ) ) {
            return;
        }
        if( !UsusProjectSupport.asUsusProject( underlyingResource.getProject() ).isUsusProject() ) {
            return;
        }

        isValid = true;
        classname = new Classname( binding.getName() );
        packagename = Packagename.of( binding.getPackage().getName(), binding.getPackage().getJavaElement() );
    }

    public boolean isValid() {
        return isValid;
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
