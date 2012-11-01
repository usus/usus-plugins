package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
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
        return connectCurrentTypeAnd( BoundTypeConverter.wrap( node ) );
    }

    // expressions

    @Override
    public boolean visit( SimpleName node ) {
        if( node.isDeclaration() ) {
            return false;
        }
        return connectCurrentTypeAnd( BoundTypeConverter.wrap( node ) );
    }

    @Override
    public boolean visit( MethodInvocation node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression == null ) {
            return connectCurrentTypeAnd( BoundTypeConverter.wrap( node ) );
        }
        return true;
    }

    @Override
    public boolean visit( FieldAccess node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression == null ) {
            return connectCurrentTypeAnd( BoundTypeConverter.wrap( node ) );
        }
        return true;
    }

    private boolean connectCurrentTypeAnd( WrappedTypeBinding targetType ) {
        if( currentType != null && targetType != null && targetType.isValid() && !currentType.equals( targetType ) ) {
            getMetricsWriter().addClassReference( currentType, targetType );
        }
        return false;
    }

    private void setCurrentType( AbstractTypeDeclaration node ) {
        currentType = BoundTypeConverter.wrap( node );
    }

}
