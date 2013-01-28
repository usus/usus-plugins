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
import org.projectusus.metrics.util.ClassValueVisitor;
import org.projectusus.metrics.util.MethodValueVisitor;

public class CollectorTestHelper {

    protected ClassValueVisitor classVisitor;
    protected MethodValueVisitor methodVisitor;
    protected ASTNodeHelper nodeHelper;

    protected ASTNodeHelper setupNodeHelperForMethod( String classname ) throws JavaModelException {
        ASTNodeHelper helper = setupNodeHelperForClass();
        TypeDeclaration parent = setupMockFor( TypeDeclaration.class, classname );
        when( helper.findEnclosingClassOf( org.mockito.Matchers.any( MethodDeclaration.class ) ) ).thenReturn( parent );
        when( Integer.valueOf( helper.getStartPositionFor( org.mockito.Matchers.any( ASTNode.class ) ) ) ).thenReturn( Integer.valueOf( 1 ) );
        return helper;
    }

    protected ASTNodeHelper setupNodeHelperForClass() throws JavaModelException {
        ASTNodeHelper helper = mock( ASTNodeHelper.class );
        ITypeBinding typeBinding = createTypeBinding();
        when( helper.resolveBindingOf( org.mockito.Matchers.any( AbstractTypeDeclaration.class ) ) ).thenReturn( typeBinding );
        return helper;
    }

    protected <T extends AbstractTypeDeclaration> T setupMockFor( Class<T> type, String name ) {
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
