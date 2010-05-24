package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleType;

public class BoundType {

    private IFile underlyingResource;
    private final Classname classname;
    private final Packagename packagename;
    private final boolean fromSource;

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
        classname = new Classname( binding.getName() );
        packagename = Packagename.of( binding.getPackage().getName() );
        fromSource = binding.isFromSource();
        try {
            underlyingResource = (IFile)binding.getJavaElement().getUnderlyingResource();
        } catch( Throwable t ) {
            underlyingResource = null;
        }

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

    public boolean isFromSource() {
        return fromSource;
    }
}
