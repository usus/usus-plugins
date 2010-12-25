package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.filerelations.model.BoundTypeConverter;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.metrics.MetricsCollector;

public class ACDCollector extends MetricsCollector {

    private WrappedTypeBinding currentType;

    public ACDCollector() {
        super();
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
        return false;
    }

    @Override
    public boolean visit( SimpleType node ) {
        visitType( BoundTypeConverter.wrap( node ) );
        return false;
    }

    // expressions

    @Override
    public boolean visit( SimpleName node ) {
        if( !node.isDeclaration() ) {
            IBinding binding = node.resolveBinding();
            if( binding instanceof IVariableBinding ) {
                visitType( BoundTypeConverter.wrap( (IVariableBinding)binding ) );
            }
        }
        return false;
    }

    // echt überflüssig, wie es scheint:
    // @Override
    // public boolean visit( QualifiedName node ) {
    // Name qualifier = node.getQualifier();
    // if( qualifier == null ) {
    // return false;
    // }
    // if( qualifier.isSimpleName() ) {
    // visitType( BoundType.of( (SimpleName)qualifier ) );
    // } else if( qualifier.isQualifiedName() ) {
    // visitType( BoundType.of( (QualifiedName)qualifier ) );
    // }
    // return false;
    // }

    @Override
    public boolean visit( MethodInvocation node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression != null ) {
            visitExpression( targetExpression );
        } else {
            visitType( BoundTypeConverter.wrap( node ) );
        }
        return false;
    }

    @Override
    public boolean visit( FieldAccess node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression != null ) {
            visitExpression( targetExpression );
        } else {
            visitType( BoundTypeConverter.wrap( node ) );
        }
        return false;
    }

    private void visitType( WrappedTypeBinding targetType ) {
        if( currentType != null && targetType != null && targetType.isValid() && !currentType.equals( targetType ) ) {
            getMetricsWriter().addClassReference( currentType, targetType );
        }
    }

    private void visitExpression( Expression targetExpression ) {
        if( targetExpression.getNodeType() == ASTNode.SIMPLE_NAME ) {
            visitType( BoundTypeConverter.wrap( (SimpleName)targetExpression ) );
        }
    }

    private void setCurrentType( AbstractTypeDeclaration node ) {
        currentType = BoundTypeConverter.wrap( node );
    }

}
