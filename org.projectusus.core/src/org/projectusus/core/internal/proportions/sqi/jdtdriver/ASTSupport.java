package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;

public abstract class ASTSupport {

    public static AbstractTypeDeclaration findEnclosingClass( ASTNode node ) {
        ASTNode enclosingClass = node;
        while( enclosingClass != null && !(enclosingClass instanceof AbstractTypeDeclaration) ) {
            enclosingClass = enclosingClass.getParent();
        }
        return (AbstractTypeDeclaration)enclosingClass;
    }

}
