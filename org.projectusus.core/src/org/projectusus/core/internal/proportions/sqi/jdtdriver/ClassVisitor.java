package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.internal.proportions.sqi.FileRawData;
import org.projectusus.core.internal.proportions.sqi.WorkspaceRawData;

public class ClassVisitor extends ASTVisitor {

    @Override
    public boolean visit( TypeDeclaration node ) {
        if( node.isInterface() ) {
            return false;
        }
        return addAbstractTypeDeclaration( node );
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        return addAbstractTypeDeclaration( node );
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        return addAbstractTypeDeclaration( node );
    }

    private boolean addAbstractTypeDeclaration( AbstractTypeDeclaration node ) {
        FileRawData currentFileResults = WorkspaceRawData.getInstance().getCurrentProjectRawData().getCurrentFileRawData();
        currentFileResults.addClass( node );
        return true;
    }
}
