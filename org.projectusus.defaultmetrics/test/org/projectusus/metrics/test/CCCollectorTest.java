package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfClasses;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfMethods;
import static org.projectusus.metrics.util.TypeBindingMocker.createFile;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.CCCollector;
import org.projectusus.metrics.util.MethodValueVisitor;

public class CCCollectorTest extends CollectorTestHelper {

    private static final String CLASS_NAME = "ClassName";
    private static final String OUTER_CLASS_NAME = "OuterClassName";
    private static final String INNER_METHOD = "innerMethod";
    private static final String METHOD = "method";
    private static final String CLASS_NAME_METHOD = CLASS_NAME + "." + METHOD + "()";
    private CCCollector collector;
    private MethodDeclaration method;

    @Before
    public void setup() throws JavaModelException {
        methodVisitor = new MethodValueVisitor( MetricsResults.CC );
        nodeHelper = setupNodeHelperForMethod( CLASS_NAME );

        UsusModelProvider.clear( nodeHelper );

        method = setupMethodDeclMock( METHOD );

        collector = new CCCollector();
        collector.setup( createFile(), UsusModelProvider.getMetricsWriter() );

        // start the visit:
        collector.init( method );
    }

    @Test
    public void noCalculationYieldsCC1() {
        checkMethodYieldsCC( 1 );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
    }

    @Test
    public void oneCalculationYieldsCC2() {
        collector.calculate( null, 1 );
        checkMethodYieldsCC( 2 );
    }

    @Test
    public void twoCalculationsYieldCC3() {
        collector.calculate( null, 1 );
        collector.calculate( null, 1 );
        checkMethodYieldsCC( 3 );
    }

    @Test
    public void oneCalculationWith5YieldsCC6() {
        collector.calculate( null, 5 );

        checkMethodYieldsCC( 6 );
    }

    @Test
    public void calculationsAreSummedUp() {
        collector.calculate( null, 5 );
        collector.calculate( null, 2 );
        collector.calculate( null, 1 );

        checkMethodYieldsCC( 9 );
    }

    @Test
    public void methodInsideMethodYieldsCC2() {
        MethodDeclaration innerMethod = setupMethodDeclMock( INNER_METHOD );

        collector.init( innerMethod );
        collector.commit( innerMethod );

        // now we need to make sure that the outer method has a different enclosing class and start position in the file:
        TypeDeclaration parent = setupMockFor( TypeDeclaration.class, OUTER_CLASS_NAME );
        when( nodeHelper.findEnclosingClassOf( org.mockito.Matchers.any( MethodDeclaration.class ) ) ).thenReturn( parent );
        when( Integer.valueOf( nodeHelper.getStartPositionFor( org.mockito.Matchers.any( ASTNode.class ) ) ) ).thenReturn( Integer.valueOf( 100 ) );

        collector.commit( method );
        methodVisitor.visit();
        assertEquals( 1, methodVisitor.getValueMap().get( OUTER_CLASS_NAME + "." + METHOD + "()" ).intValue() );
        assertEquals( 1, methodVisitor.getValueMap().get( CLASS_NAME + "." + INNER_METHOD + "()" ).intValue() );
    }

    private void checkMethodYieldsCC( int cc ) {
        collector.commit( method );

        methodVisitor.visit();
        assertEquals( cc, methodVisitor.getValueMap().get( CLASS_NAME_METHOD ).intValue() );
    }

}
