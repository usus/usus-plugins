package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.projectusus.core.project2.UsusProjectSupport;

public class TypeBindingChecker {

    private static final String JAVA_FILE_EXTENSION = "java"; //$NON-NLS-1$

    public WrappedTypeBinding checkForRelevanceAndWrap( ITypeBinding typeBinding ) {
        ITypeBinding relevantBinding = findRelevantBinding( typeBinding );
        IFile underlyingResource = checkUnderlyingResource( relevantBinding );
        if( relevantBinding != null && underlyingResource != null ) {
            return new WrappedTypeBinding( underlyingResource, relevantBinding );
        }
        return null;
    }

    private ITypeBinding findRelevantBinding( ITypeBinding typeBinding ) {
        ITypeBinding binding = typeBinding;
        if( binding == null || binding.isPrimitive() ) {
            return null;
        }
        binding = binding.getErasure();
        if( binding == null || binding.isPrimitive() ) {
            return null;
        }
        if( binding.isTypeVariable() || binding.isCapture() || binding.isWildcardType() ) {
            return null;
        }
        if( binding.isAnonymous() ) {
            return findRelevantBinding( binding.getDeclaringClass() );
        }
        return binding;
    }

    private IFile checkUnderlyingResource( ITypeBinding binding ) {
        if( binding == null ) {
            return null;
        }
        try {
            IFile resource = (IFile)binding.getJavaElement().getUnderlyingResource();
            if( isJavaFile( resource ) && isInsideUsusProject( resource ) ) {
                return resource;
            }
        } catch( Throwable t ) {
            // ignore
        }
        return null;
    }

    private boolean isInsideUsusProject( IFile underlyingResource ) {
        return UsusProjectSupport.asUsusProject( underlyingResource.getProject() ).isUsusProject();
    }

    private boolean isJavaFile( IFile underlyingResource ) {
        return underlyingResource != null && underlyingResource.getFileExtension().equals( JAVA_FILE_EXTENSION );
    }
}
