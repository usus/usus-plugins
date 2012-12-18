package org.projectusus.core.filerelations.model;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;

public class ASTNodeHelper {

    public ITypeBinding resolveBindingOf( AbstractTypeDeclaration node ) {
        return node.resolveBinding();
    }

    public ITypeBinding resolveBindingOf( SimpleType node ) {
        return node.resolveBinding();
    }

    public IBinding resolveBindingOf( SimpleName node ) {
        return node.resolveBinding();
    }

    public ITypeBinding resolveTypeBindingOf( FieldAccess node ) {
        return node.resolveTypeBinding();
    }

    public ITypeBinding resolveTypeBindingOf( QualifiedName qualifier ) {
        return qualifier.resolveTypeBinding();
    }

    public AbstractTypeDeclaration findEnclosingClassOf( ASTNode node ) {
        ASTNode enclosingClass = node;
        while( enclosingClass != null && !(enclosingClass instanceof AbstractTypeDeclaration) ) {
            enclosingClass = enclosingClass.getParent();
        }
        return (AbstractTypeDeclaration)enclosingClass;
    }

}
