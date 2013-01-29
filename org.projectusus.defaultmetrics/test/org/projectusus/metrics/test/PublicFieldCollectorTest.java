package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.PublicFieldCollector;
import org.projectusus.metrics.util.ClassValueVisitor;
import org.projectusus.metrics.util.Setup;

public class PublicFieldCollectorTest extends CollectorTestHelper {

    private static final String CLASS_NAME = "ClassName";
    private static final String ANOTHER_CLASS_NAME = "AnotherClassName";
    private PublicFieldCollector collector;
    private TypeDeclaration node;

    @Before
    public void setup() throws JavaModelException {
        classVisitor = new ClassValueVisitor( MetricsResults.PUBLIC_FIELDS );
        nodeHelper = setupNodeHelperForClass();

        UsusModelProvider.clear( nodeHelper );
        collector = new PublicFieldCollector();
        node = Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME );

        collector.init( node );
    }

    @Test
    public void noCalculationsYieldCountOf0() {
        collector.commit( node );
        classVisitor.visit();
        assertEquals( 0, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void publicStaticFinalFieldIsNotCounted() {
        collector.calculate( Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL );

        collector.commit( node );

        classVisitor.visit();
        assertEquals( 0, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void publicNonstaticNonfinalFieldIsCounted() {
        collector.calculate( Modifier.PUBLIC );

        collector.commit( node );

        classVisitor.visit();
        assertEquals( 1, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void combinationsOfPublicNonstaticNonfinalFieldIsCounted() {
        collector.calculate( Modifier.PUBLIC );
        collector.calculate( Modifier.PUBLIC | Modifier.STATIC );
        collector.calculate( Modifier.PUBLIC | Modifier.FINAL );

        collector.commit( node );

        classVisitor.visit();
        assertEquals( 3, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void publicFieldsInTwoClassesAreCountedSeparately() {
        collector.calculate( Modifier.PUBLIC );

        TypeDeclaration node2 = Setup.setupMockWithStartPosition( TypeDeclaration.class, PublicFieldCollectorTest.ANOTHER_CLASS_NAME, 100, nodeHelper );

        collector.init( node2 );
        collector.calculate( Modifier.PUBLIC );
        collector.commit( node2 );

        collector.commit( node );

        classVisitor.visit();
        assertEquals( 1, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, classVisitor.getValueMap().get( ANOTHER_CLASS_NAME ).intValue() );
    }

}
