package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
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

    private static BoundType of( final ITypeBinding type ) {
        if( type == null ) {
            return null;
        }
        ITypeBinding erasedType = type.getErasure();
        if( erasedType == null || isATypeVariable( erasedType ) ) {
            return null;
        }
        try {
            IFile underlyingResource = (IFile)erasedType.getJavaElement().getUnderlyingResource();
            if( !underlyingResource.getFileExtension().equals( JAVA ) ) {
                return null;
            }
            IUSUSProject adapter = (IUSUSProject)underlyingResource.getProject().getAdapter( IUSUSProject.class );
            if( adapter == null || !adapter.isUsusProject() ) {
                return null;
            }
        } catch( Throwable t ) {
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

}
