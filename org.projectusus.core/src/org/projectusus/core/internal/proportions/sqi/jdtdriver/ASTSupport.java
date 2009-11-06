package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public abstract class ASTSupport {

    public static AbstractTypeDeclaration findEnclosingClass( MethodDeclaration node ) {
        ASTNode enclosingClass = node;
        while( enclosingClass != null && !(enclosingClass instanceof AbstractTypeDeclaration) ) {
            enclosingClass = enclosingClass.getParent();
        }
        return (AbstractTypeDeclaration)enclosingClass;
    }

}
