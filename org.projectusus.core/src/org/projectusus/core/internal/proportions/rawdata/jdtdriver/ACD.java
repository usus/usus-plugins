package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;

public class ACD extends ASTVisitor {

    private String currentType;

    // current type

    @Override
    public boolean visit( TypeDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    // referenced types

    @Override
    public boolean visit( ArrayType node ) {
        if( currentType == null ) {
            return true;
        }
        Type element = node.getElementType();
        if( element.isSimpleType() ) {
            visit( (SimpleType)element );
        }
        return true;
    }

    @Override
    public boolean visit( SimpleType node ) {
        if( currentType == null ) {
            return true;
        }
        ITypeBinding binding = node.resolveBinding();
        if( binding != null ) {
            String fullyQualifiedTypeName = binding.getQualifiedName();
            WorkspaceRawData.getInstance().getAcdModel().addClassReference( currentType, fullyQualifiedTypeName );
        }
        return true;
    }

    /**
     * QualifiedType -- it should not be necessary to treat it:
     * 
     * A type like "A.B" can be represented either of two ways:
     * <ol>
     * <li>
     * <code>QualifiedType(SimpleType(SimpleName("A")),SimpleName("B"))</code></li>
     * <li>
     * <code>SimpleType(QualifiedName(SimpleName("A"),SimpleName("B")))</code></li>
     * </ol>
     * 
     * Therefore, it should be sufficient to treat SimpleType.
     */

    private void setCurrentType( AbstractTypeDeclaration node ) {
        ITypeBinding binding = node.resolveBinding();
        if( binding != null ) {
            currentType = binding.getQualifiedName();
        }
    }

}
