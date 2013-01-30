package org.projectusus.metrics;

import java.util.Stack;

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
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.BoundTypeConverter;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.metrics.MetricsCollector;

public class ACDCollector extends MetricsCollector {

    private Stack<WrappedTypeBinding> types = new Stack<WrappedTypeBinding>();

    public void connectTypes( WrappedTypeBinding sourceType, WrappedTypeBinding targetType ) {
        getMetricsWriter().addClassReference( sourceType, targetType );
    }

    private void addCurrentType( AbstractTypeDeclaration node ) {
        addCurrentType( new BoundTypeConverter( new ASTNodeHelper() ).wrap( node ) );
    }

    private void addCurrentType( WrappedTypeBinding wrap ) {
        types.push( wrap );
    }

    private void connectCurrentTypeAnd( WrappedTypeBinding targetType ) {
        if( currentType() != null && targetType != null && targetType.isValid() && !currentType().equals( targetType ) ) {
            connectTypes( currentType(), targetType );
        }
    }

    private void dropCurrentType() {
        if( !types.empty() ) {
            types.pop();
        }
    }

    // these must be ignored:
    @Override
    public boolean visit( ImportDeclaration node ) {
        return false;
    }

    // capturing the current type:
    @Override
    public boolean visit( TypeDeclaration node ) {
        addCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        addCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        addCurrentType( node );
        return true;
    }

    // resetting the current type:

    @Override
    public void endVisit( TypeDeclaration node ) {
        dropCurrentType();
    }

    @Override
    public void endVisit( AnnotationTypeDeclaration node ) {
        dropCurrentType();
    }

    @Override
    public void endVisit( EnumDeclaration node ) {
        dropCurrentType();
    }

    // referenced types

    @Override
    public boolean visit( SimpleType node ) {
        connectCurrentTypeAnd( new BoundTypeConverter( new ASTNodeHelper() ).wrap( node ) );
        return true;
    }

    // expressions

    @Override
    public boolean visit( SimpleName node ) {
        if( node.isDeclaration() ) {
            return false;
        }
        connectCurrentTypeAnd( new BoundTypeConverter( new ASTNodeHelper() ).wrap( node ) );
        return true;
    }

    @Override
    public boolean visit( MethodInvocation node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression == null ) {
            connectCurrentTypeAnd( new BoundTypeConverter( new ASTNodeHelper() ).wrap( node ) );
        }
        return true;
    }

    @Override
    public boolean visit( FieldAccess node ) {
        Expression targetExpression = node.getExpression();
        if( targetExpression == null ) {
            connectCurrentTypeAnd( new BoundTypeConverter( new ASTNodeHelper() ).wrap( node ) );
        }
        return true;
    }

    private WrappedTypeBinding currentType() {
        if( types.empty() ) {
            return null;
        }
        return types.peek();
    }
}
