package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleType;

public class BoundType {

    private final ITypeBinding binding;

    public static BoundType of( AbstractTypeDeclaration node ) {
        return node == null ? null : of( node.resolveBinding() );
    }

    public static BoundType of( SimpleType node ) {
        return node == null ? null : of( node.resolveBinding() );
    }

    private static BoundType of( final ITypeBinding type ) {
        if( type == null ) {
            return null;
        }
        ITypeBinding erasedType = type.getErasure();
        if( erasedType == null || isATypeVariable( erasedType ) ) {
            return null;
        }
        return new BoundType( erasedType );
    }

    private static boolean isATypeVariable( ITypeBinding erasedType ) {
        return erasedType.isTypeVariable() || erasedType.isCapture() || erasedType.isWildcardType();
    }

    private BoundType( ITypeBinding binding ) {
        this.binding = binding;
    }

    public IFile getUnderlyingResource() {
        try {
            return (IFile)binding.getJavaElement().getUnderlyingResource();
        } catch( Throwable t ) {
            return null;
        }
    }

    public Classname getClassname() {
        return new Classname( binding.getName() );
    }

    public Packagename getPackagename() {
        return Packagename.of( binding.getPackage().getName() );
    }

    public boolean isFromSource() {
        return binding.isFromSource();
    }
}
