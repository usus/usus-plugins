package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfClasses;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfMethods;
import static org.projectusus.metrics.util.TypeBindingMocker.createFile;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.MLCollector;
import org.projectusus.metrics.util.MethodValueVisitor;

public class MLCollectorTest extends CollectorTestHelper {

    private static final String CLASS_NAME = "ClassName";
    private static final String METHOD = "method";
    private static final String CLASS_NAME_METHOD = CLASS_NAME + "." + METHOD + "()";
    private MLCollector collector;
    private MethodDeclaration method;

    @Before
    public void setup() throws JavaModelException {
        visitor = new MethodValueVisitor( MetricsResults.ML );
        nodeHelper = setupNodeHelperForMethod( CLASS_NAME );

        UsusModelProvider.clear( nodeHelper );

        method = setupMethodDeclMock( METHOD );

        collector = new MLCollector();
        collector.setup( createFile(), UsusModelProvider.getMetricsWriter() );

        // start the visit:
        collector.init( method );
    }

    @Test
    public void methodWithoutStatementsHasML0() {
        checkMethodYieldsML( 0 );

        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
    }

    @Test
    public void methodWithOneStatementHasML1() {
        collector.calculate( 1 );

        checkMethodYieldsML( 1 );
    }

    @Test
    public void methodWithTwoSeparateStatementsHasML2() {
        collector.calculate( 1 );
        collector.calculate( 1 );

        checkMethodYieldsML( 2 );
    }

    @Test
    public void methodWithMultipleGroupedStatements() {
        collector.calculate( 3 );
        collector.calculate( 5 );
        collector.calculate( 2 );

        checkMethodYieldsML( 10 );
    }

    private void checkMethodYieldsML( int ml ) {
        collector.commit( method );

        visitor.visit();
        assertEquals( ml, visitor.getValueMap().get( CLASS_NAME_METHOD ).intValue() );
    }

}
