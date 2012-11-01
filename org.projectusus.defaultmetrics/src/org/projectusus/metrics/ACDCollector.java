package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.filerelations.model.BoundTypeConverter;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.metrics.MetricsCollector;

public class ACDCollector extends MetricsCollector {

    private WrappedTypeBinding currentType;

    private void resetCurrentType() {
        currentType = null;
    }

    // resetting the current type:

    @Override
    public boolean visit( CompilationUnit node ) {
        resetCurrentType();
        return true;
    }


    // these must be ignored:
    @Override
    public boolean visit( ImportDeclaration node ) {
        return false;
    }

    // capturing the current type:
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
