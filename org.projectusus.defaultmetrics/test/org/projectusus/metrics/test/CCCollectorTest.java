package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfClasses;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfMethods;
import static org.projectusus.metrics.util.TypeBindingMocker.createFile;
import static org.projectusus.metrics.util.TypeBindingMocker.createTypeBinding;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.CCCollector;
import org.projectusus.metrics.util.MethodValueVisitor;

public class CCCollectorTest {

    private CCCollector collector;
    private MethodValueVisitor visitor = new MethodValueVisitor( MetricsResults.CC );
    private MethodDeclaration method;
    private ASTNodeHelper nodeHelper;

    @Before
    public void setup() throws JavaModelException {
        nodeHelper = mock( ASTNodeHelper.class );
        String classname = "ClassName";
        ITypeBinding typeBinding = createTypeBinding();
        when( nodeHelper.resolveBindingOf( org.mockito.Matchers.any( AbstractTypeDeclaration.class ) ) ).thenReturn( typeBinding );
        TypeDeclaration parent = setupMockFor( TypeDeclaration.class, classname );
        when( nodeHelper.findEnclosingClassOf( org.mockito.Matchers.any( MethodDeclaration.class ) ) ).thenReturn( parent );
        when( Integer.valueOf( nodeHelper.getStartPositionFor( org.mockito.Matchers.any( ASTNode.class ) ) ) ).thenReturn( Integer.valueOf( 1 ) );

        UsusModelProvider.clear( nodeHelper );

        method = setupMethodDeclMock( "method" );

        collector = new CCCollector();
        collector.setup( createFile(), UsusModelProvider.getMetricsWriter() );

        // start the visit:
        collector.visit( method );
    }

    @Test
    public void emptyMethodHasCC1() {
        collector.endVisit( method );

        visitor.visit();
        assertEquals( 1, visitor.getValueSum() );
        assertEquals( "ClassName.method()", visitor.getName() );

        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
    }

    @Test
    public void oneWhileHasCC2() {
        WhileStatement stmt = mock( WhileStatement.class );
        collector.visit( stmt );
        checkMethodYieldsCC( 2 );
    }

    @Test
    public void oneDoHasCC2() {
        DoStatement stmt = mock( DoStatement.class );
        collector.visit( stmt );
        checkMethodYieldsCC( 2 );
    }

    @Test
    public void oneForHasCC2() {
        ForStatement stmt = mock( ForStatement.class );
        collector.visit( stmt );
        checkMethodYieldsCC( 2 );
    }

    @Test
    public void oneForeachHasCC2() {
        EnhancedForStatement stmt = mock( EnhancedForStatement.class );
        collector.visit( stmt );
        checkMethodYieldsCC( 2 );
    }

    @Test
    public void twoIfHasCC3() {
        IfStatement stmt = mock( IfStatement.class );
        collector.visit( stmt );
        collector.visit( stmt );
        checkMethodYieldsCC( 3 );
    }

    @Test
    public void oneLogicalOrBitwiseHasCC1() {
        InfixExpression stmt = mock( InfixExpression.class );
        when( stmt.getOperator() ).thenReturn( Operator.AND );
        collector.visit( stmt );

        checkMethodYieldsCC( 1 );
    }

    @Test
    public void threeCaseHasCC4() {
        SwitchCase stmt = mock( SwitchCase.class );
        collector.visit( stmt );
        collector.visit( stmt );
        collector.visit( stmt );
        checkMethodYieldsCC( 4 );
    }

    @Test
    public void oneCatchHasCC2() {
        CatchClause stmt = mock( CatchClause.class );
        collector.visit( stmt );
        checkMethodYieldsCC( 2 );
    }

    @Test
    public void threeConditionalsHasCC4() {
        ConditionalExpression stmt = mock( ConditionalExpression.class );
        collector.visit( stmt );
        collector.visit( stmt );
        collector.visit( stmt );
        checkMethodYieldsCC( 4 );
    }

    @Test
    public void infixExpressionWithThreeOperandsYieldsCC3() {
        InfixExpression stmt = mock( InfixExpression.class );
        when( stmt.getOperator() ).thenReturn( Operator.CONDITIONAL_AND );
        List<?> operands = new ArrayList<Integer>();
        operands.add( null );
        when( stmt.extendedOperands() ).thenReturn( operands );
        collector.visit( stmt );

        checkMethodYieldsCC( 3 );
    }

    @Test
    public void methodInsideMethodYieldsCC2() {
        MethodDeclaration innerMethod = setupMethodDeclMock( "innerMethod" );

        collector.visit( innerMethod );
        collector.endVisit( innerMethod );

        // now we need to make sure that the outer method has a different enclosing class and start position in the file:
        TypeDeclaration parent = setupMockFor( TypeDeclaration.class, "OuterClassName" );
        when( nodeHelper.findEnclosingClassOf( org.mockito.Matchers.any( MethodDeclaration.class ) ) ).thenReturn( parent );
        when( Integer.valueOf( nodeHelper.getStartPositionFor( org.mockito.Matchers.any( ASTNode.class ) ) ) ).thenReturn( Integer.valueOf( 100 ) );

        // this is the sum of the CC's for both methods:
        checkMethodYieldsCC( 2 );
    }

    private void checkMethodYieldsCC( int cc ) {
        collector.endVisit( method );

        visitor.visit();
        assertEquals( cc, visitor.getValueSum() );
    }

    private <T extends AbstractTypeDeclaration> T setupMockFor( Class<T> type, String name ) {
        T node = mock( type );
        SimpleName nodename = createSimpleNameMockWithName( name );
        when( node.getName() ).thenReturn( nodename );
        return node;
    }

    private MethodDeclaration setupMethodDeclMock( String name ) {
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
