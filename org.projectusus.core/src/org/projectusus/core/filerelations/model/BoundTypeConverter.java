package org.projectusus.core.filerelations.model;

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

public class BoundTypeConverter {

    private final TypeBindingChecker bindingChecker = new TypeBindingChecker();
    private ASTNodeHelper nodeHelper;

    public BoundTypeConverter( ASTNodeHelper nodeHelper ) {
        this.nodeHelper = nodeHelper;
    }

    public WrappedTypeBinding wrap( AbstractTypeDeclaration node ) {
        if( node != null ) {
            return wrap( nodeHelper.resolveBindingOf( node ) );
        }
        return null;
    }

    public WrappedTypeBinding wrap( SimpleType node ) {
        if( node != null ) {
            return wrap( nodeHelper.resolveBindingOf( node ) );
        }
        return null;
    }

    public WrappedTypeBinding wrap( SimpleName node ) {
        if( node != null ) {
            IBinding binding = nodeHelper.resolveBindingOf( node );
            if( binding instanceof ITypeBinding ) {
                return wrap( ((ITypeBinding)binding) );
            }
            if( binding instanceof IVariableBinding ) {
                return wrap( ((IVariableBinding)binding).getDeclaringClass() );
            }
        }
        return null;
    }

    public WrappedTypeBinding wrap( MethodInvocation node ) {
        if( node != null ) {
            IMethodBinding methodBinding = node.resolveMethodBinding();
            if( methodBinding != null ) {
                return wrap( methodBinding.getDeclaringClass() );
            }
        }
        return null;
    }

    public WrappedTypeBinding wrap( FieldAccess node ) {
        if( node != null ) {
            return wrap( nodeHelper.resolveTypeBindingOf( node ) );
        }
        return null;
    }

    public WrappedTypeBinding wrap( QualifiedName qualifier ) {
        if( qualifier != null ) {
            return wrap( nodeHelper.resolveTypeBindingOf( qualifier ) );
        }
        return null;
    }

    private WrappedTypeBinding wrap( ITypeBinding binding ) {
        return bindingChecker.checkForRelevanceAndWrap( binding );
    }
}
