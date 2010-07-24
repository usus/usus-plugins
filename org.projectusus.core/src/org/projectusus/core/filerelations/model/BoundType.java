package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.projectusus.core.internal.project.IUSUSProject;

public class BoundType {

    private static final String JAVA = "java"; //$NON-NLS-1$
    private IFile underlyingResource;
    private final Classname classname;
    private final Packagename packagename;

    public static BoundType of( AbstractTypeDeclaration node ) {
        return node == null ? null : of( node.resolveBinding() );
    }

    public static BoundType of( SimpleType node ) {
        return node == null ? null : of( node.resolveBinding() );
    }

    public static BoundType of( SimpleName node ) {
        if( node != null ) {
            IBinding binding = node.resolveBinding();
            if( binding instanceof ITypeBinding ) {
                return of( (ITypeBinding)binding );
            }
        }
        return null;
    }

    public static BoundType of( MethodInvocation node ) {
        if( node != null ) {
            IMethodBinding methodBinding = node.resolveMethodBinding();
            if( methodBinding != null ) {
                return of( methodBinding.getDeclaringClass() );
            }
        }
        return null;
    }

    private static BoundType of( final ITypeBinding type ) {
        if( type == null || type.isPrimitive() ) {
            return null;
        }
        ITypeBinding erasedType = type.getErasure();
        if( invalidType( erasedType ) ) {
            return null;
        }
        return new BoundType( erasedType );
    }

    private static boolean invalidType( ITypeBinding erasedType ) {
        return erasedType == null || isATypeVariable( erasedType ) || resourceIsNotInUsusProject( erasedType );
    }

    private static boolean resourceIsNotInUsusProject( ITypeBinding binding ) {
        IFile underlyingResource = determineUnderlyingResource( binding );
        if( underlyingResource == null || !underlyingResource.getFileExtension().equals( JAVA ) ) {
            return true;
        }
        IUSUSProject adapter = (IUSUSProject)underlyingResource.getProject().getAdapter( IUSUSProject.class );
        if( adapter == null || !adapter.isUsusProject() ) {
            return true;
        }
        return false;
    }

    private static IFile determineUnderlyingResource( ITypeBinding binding ) {
        try {
            return (IFile)binding.getJavaElement().getUnderlyingResource();
        } catch( Throwable t ) {
            return null;
        }
    }

    private static boolean isATypeVariable( ITypeBinding binding ) {
        return binding.isTypeVariable() || binding.isCapture() || binding.isWildcardType();
    }

    private BoundType( ITypeBinding binding ) {
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
