package org.projectusus.metrics.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.metrics.util.TypeBindingMocker.createTypeBinding;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.metrics.util.MethodValueVisitor;

public class CollectorTestHelper {

    protected MethodValueVisitor visitor;
    protected ASTNodeHelper nodeHelper;

    public ASTNodeHelper setupNodeHelperForMethod( String classname ) throws JavaModelException {
        ASTNodeHelper nodeHelper = mock( ASTNodeHelper.class );
        ITypeBinding typeBinding = createTypeBinding();
        when( nodeHelper.resolveBindingOf( org.mockito.Matchers.any( AbstractTypeDeclaration.class ) ) ).thenReturn( typeBinding );
        TypeDeclaration parent = setupMockFor( TypeDeclaration.class, classname );
        when( nodeHelper.findEnclosingClassOf( org.mockito.Matchers.any( MethodDeclaration.class ) ) ).thenReturn( parent );
        when( Integer.valueOf( nodeHelper.getStartPositionFor( org.mockito.Matchers.any( ASTNode.class ) ) ) ).thenReturn( Integer.valueOf( 1 ) );
        return nodeHelper;
    }

    private <T extends AbstractTypeDeclaration> T setupMockFor( Class<T> type, String name ) {
        T node = mock( type );
        SimpleName nodename = createSimpleNameMockWithName( name );
        when( node.getName() ).thenReturn( nodename );
        return node;
    }

    protected MethodDeclaration setupMethodDeclMock( String name ) {
        MethodDeclaration node = mock( MethodDeclaration.class );
        SimpleName nodename = createSimpleNameMockWithName( name );
        when( node.getName() ).thenReturn( nodename );
        return node;
    }

    private SimpleName createSimpleNameMockWithName( String nodename ) {
        SimpleName name = mock( SimpleName.class );
        when( name.toString() ).thenReturn( nodename );
        return name;
    }

}
