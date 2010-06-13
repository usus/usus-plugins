package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.filerelations.model.BoundType;

public class ACDCollector extends Collector {

    private BoundType currentType;

    public ACDCollector( IFile file ) {
        super( file );
    }

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
        BoundType targetType = BoundType.of( node );
        if( currentType != null && targetType != null ) {
            getMetricsWriter().addClassReference( currentType, targetType );
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

    @Override
    public boolean visit( MethodInvocation node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression != null ) {
            if( targetExpression.getNodeType() == ASTNode.SIMPLE_NAME ) {
                BoundType targetType = BoundType.of( (SimpleName)targetExpression );
                if( currentType != null && targetType != null ) {
                    // we found a static method invocation
                    getMetricsWriter().addClassReference( currentType, targetType );
                }
            }
        } else {
            // method invocation without target expression
            BoundType declaringType = BoundType.of( node );
            if( currentType != null && declaringType != null && !currentType.equals( declaringType ) ) {
                getMetricsWriter().addClassReference( currentType, declaringType );
            }
            // if( declaringType == null ) {
            // System.out.println( "Declaring type of invocation " + node.toString() + " in class " + currentType.getClassname() + " is null" );
            // }
        }
        return true;
    }

    // @Override
    // public boolean visit( QualifiedName node ) {
    // Name qualifier = node.getQualifier();
    // if( qualifier == null ) {
    // return true;
    // }
    // int nodeType = qualifier.getNodeType();
    // ITypeBinding typeBinding = qualifier.resolveTypeBinding();
    // if( typeBinding == null ) {
    // return true;
    // }
    // System.out.println( "QualifiedName: " + node.toString() + " type: " + nodeType + " type binding: " + typeBinding.getName() );
    // return true;
    // }

    private void setCurrentType( AbstractTypeDeclaration node ) {
        currentType = BoundType.of( node );
    }

}
