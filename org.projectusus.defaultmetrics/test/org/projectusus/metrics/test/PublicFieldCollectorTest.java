package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.FieldDeclaration;
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

        collector.visit( node );
    }

    @Test
    public void emptyClassHasNoPublicFields() {
        collectorEndVisitAndVisitorVisit();
        assertEquals( 0, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void publicStaticFinalFieldIsNotCounted() {
        visitFieldWithModifiers( Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL );

        collectorEndVisitAndVisitorVisit();
        assertEquals( 0, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void publicNonstaticNonfinalFieldIsCounted() {
        visitFieldWithModifiers( Modifier.PUBLIC );

        collectorEndVisitAndVisitorVisit();
        assertEquals( 1, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void combinationsOfPublicNonstaticNonfinalFieldIsCounted() {
        visitFieldWithModifiers( Modifier.PUBLIC );
        visitFieldWithModifiers( Modifier.PUBLIC | Modifier.STATIC );
        visitFieldWithModifiers( Modifier.PUBLIC | Modifier.FINAL );

        collectorEndVisitAndVisitorVisit();
        assertEquals( 3, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
    }

    @Test
    public void publicFieldsInTwoClassesAreCountedSeparately() {
        visitFieldWithModifiers( Modifier.PUBLIC );

        TypeDeclaration node2 = Setup.setupMockWithStartPosition( TypeDeclaration.class, PublicFieldCollectorTest.ANOTHER_CLASS_NAME, 100, nodeHelper );

        collector.visit( node2 );
        visitFieldWithModifiers( Modifier.PUBLIC );
        collector.endVisit( node2 );

        collectorEndVisitAndVisitorVisit();
        assertEquals( 1, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, classVisitor.getValueMap().get( ANOTHER_CLASS_NAME ).intValue() );
    }

    private void visitFieldWithModifiers( int modifiers ) {
        FieldDeclaration field = mock( FieldDeclaration.class );
        when( Integer.valueOf( field.getModifiers() ) ).thenReturn( Integer.valueOf( modifiers ) );
        collector.visit( field );
    }

    private void collectorEndVisitAndVisitorVisit() {
        collector.endVisit( node );
        classVisitor.visit();
    }

}
