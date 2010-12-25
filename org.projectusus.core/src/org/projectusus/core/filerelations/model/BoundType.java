package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;

public class BoundType {

    private IFile underlyingResource;
    private final Classname classname;
    private final Packagename packagename;

    public static BoundType of( AbstractTypeDeclaration node ) {
        return node == null ? null : new WrappedTypeBinding( node.resolveBinding() ).getBoundType();
    }

    public static BoundType of( SimpleType node ) {
        return node == null ? null : new WrappedTypeBinding( node.resolveBinding() ).getBoundType();
    }

    public static BoundType of( SimpleName node ) {
        if( node != null ) {
            IBinding binding = node.resolveBinding();
            if( binding instanceof ITypeBinding ) {
                return new WrappedTypeBinding( ((ITypeBinding)binding) ).getBoundType();
            }
        }
        return null;
    }

    public static BoundType of( IVariableBinding varBinding ) {
        return new WrappedTypeBinding( varBinding.getDeclaringClass() ).getBoundType();
    }

    public static BoundType of( MethodInvocation node ) {
        if( node != null ) {
            IMethodBinding methodBinding = node.resolveMethodBinding();
            if( methodBinding != null ) {
                return new WrappedTypeBinding( methodBinding.getDeclaringClass() ).getBoundType();
            }
        }
        return null;
    }

    public static BoundType of( FieldAccess node ) {
        if( node != null ) {
            return new WrappedTypeBinding( node.resolveTypeBinding() ).getBoundType();
        }
        return null;
    }

    public static BoundType of( QualifiedName qualifier ) {
        return new WrappedTypeBinding( qualifier.resolveTypeBinding() ).getBoundType();
    }

    // kann bald weg

    private static IFile determineUnderlyingResource( ITypeBinding binding ) {
        try {
            return (IFile)binding.getJavaElement().getUnderlyingResource();
        } catch( Throwable t ) {
            return null;
        }
    }

    public BoundType( ITypeBinding binding ) {
        classname = new Classname( binding.getName() );
        packagename = Packagename.of( binding.getPackage().getName(), binding.getPackage().getJavaElement() );
        underlyingResource = determineUnderlyingResource( binding );
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
        return obj instanceof BoundType && equals( (BoundType)obj );
    }

    private boolean equals( BoundType other ) {
        return new EqualsBuilder().append( classname, other.classname ).append( packagename, other.packagename ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( classname ).append( packagename ).toHashCode();
    }
}
