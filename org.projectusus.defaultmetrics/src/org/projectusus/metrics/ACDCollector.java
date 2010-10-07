package org.projectusus.metrics;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.metrics.MetricsCollector;

public class ACDCollector extends MetricsCollector {

    private BoundType currentType;

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
        return true;
    }

    @Override
    public boolean visit( SimpleType node ) {
        BoundType targetType = BoundType.of( node );
        if( currentType != null && targetType != null ) {
            UsusModelProvider.addClassReference( currentType, targetType );
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
            visitMethodWithTargetExpression( targetExpression );
        } else {
            visitMethodWithoutTargetExpression( node );
        }
        return true;
    }

    private void visitMethodWithoutTargetExpression( MethodInvocation node ) {
        BoundType declaringType = BoundType.of( node );
        if( currentType != null && declaringType != null && !currentType.equals( declaringType ) ) {
            UsusModelProvider.addClassReference( currentType, declaringType );
        }
        // if( declaringType == null ) {
        // System.out.println( "Declaring type of invocation " + node.toString() + " in class " + currentType.getClassname() + " is null" );
        // }
    }

    private void visitMethodWithTargetExpression( Expression targetExpression ) {
        if( targetExpression.getNodeType() == ASTNode.SIMPLE_NAME ) {
            BoundType targetType = BoundType.of( (SimpleName)targetExpression );
            if( currentType != null && targetType != null ) {
                // we found a static method invocation
                UsusModelProvider.addClassReference( currentType, targetType );
            }
        }
    }

    // FieldAccess:
    // Expression . Identifier
    //
    // Note that there are several kinds of expressions that resemble field access expressions: qualified names, this expressions, and super field access expressions. The following
    // guidelines help with correct usage:
    //
    // An expression like "foo.this" can only be represented as a this expression (ThisExpression) containing a simple name. "this" is a keyword, and therefore invalid as an
    // identifier.
    // An expression like "this.foo" can only be represented as a field access expression (FieldAccess) containing a this expression and a simple name. Again, this is because
    // "this" is a keyword, and therefore invalid as an identifier.
    // An expression with "super" can only be represented as a super field access expression (SuperFieldAccess). "super" is a also keyword, and therefore invalid as an identifier.
    // An expression like "foo.bar" can be represented either as a qualified name (QualifiedName) or as a field access expression (FieldAccess) containing simple names. Either is
    // acceptable, and there is no way to choose between them without information about what the names resolve to (ASTParser may return either).
    // Other expressions ending in an identifier, such as "foo().bar" can only be represented as field access expressions (FieldAccess).

    @Override
    public boolean visit( FieldAccess node ) {
        // TODO Auto-generated method stub
        return super.visit( node );
    }

    @Override
    public boolean visit( SuperFieldAccess node ) {
        // TODO Auto-generated method stub
        return super.visit( node );
    }

    @Override
    public boolean visit( ThisExpression node ) {

        return super.visit( node );
    }

    // AST node for a qualified name. A qualified name is defined recursively as a simple name preceded by a name, which qualifies it. Expressing it this way means that the
    // qualifier and the simple name get their own AST nodes.
    //
    // QualifiedName:
    // Name . SimpleName
    //    
    // See FieldAccess for guidelines on handling other expressions that resemble qualified names.
    @Override
    public boolean visit( QualifiedName node ) {
        // TODO Auto-generated method stub
        return super.visit( node );
    }

    // public boolean visit(final SimpleName node) {
    // if (!node.isDeclaration()) {
    // final IBinding nodeBinding = node.resolveBinding();
    // if (nodeBinding instanceof IVariableBinding) {
    // ...
    // }
    // }
    // return false;
    // }

    // public class SimpleName
    // extends Name
    // AST node for a simple name. A simple name is an identifier other than a keyword, boolean literal ("true", "false") or null literal ("null").
    //
    // SimpleName:
    // Identifier

    // static import, static with declaring class

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
