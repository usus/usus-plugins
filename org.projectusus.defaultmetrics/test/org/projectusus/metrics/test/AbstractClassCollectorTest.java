package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfClasses;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.AbstractClassCollector;
import org.projectusus.metrics.util.ClassValueVisitor;
import org.projectusus.metrics.util.Setup;

public class AbstractClassCollectorTest extends CollectorTestHelper {

    private static final String ABSTRACT_CLASS_NAME = "AbstractClassName";
    private static final String ENUM_NAME = "EnumName";
    private static final String INTERFACE_NAME = "InterfaceName";
    private static final String ANNOTATION_NAME = "AnnotationName";
    private static final String CLASS_NAME = "ClassName";

    private AbstractClassCollector collector;

    @Before
    public void setup() throws JavaModelException {
        classVisitor = new ClassValueVisitor( MetricsResults.ABSTRACTNESS );
        UsusModelProvider.clear( setupNodeHelperForClass() );
        collector = new AbstractClassCollector();
    }

    @Test
    public void markAsAbstractLeadsToAbstractionCounter1() {
        collector.markAsAbstract( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME ) );

        classVisitor.visit();

        assertEquals( 1, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void markAsConcreteLeadsToAbstractionCounter0() {
        collector.markAsConcrete( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME ) );

        classVisitor.visit();

        assertEquals( 0, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void abstractnessOfMultipleClassesIsAdditive() {
        collector.markAsAbstract( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, INTERFACE_NAME ) );

        collector.markAsAbstract( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, ABSTRACT_CLASS_NAME ) );

        collector.markAsConcrete( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME ) );

        collector.markAsConcrete( Setup.setupCollectorAndMockFor( collector, EnumDeclaration.class, ENUM_NAME ) );

        collector.markAsConcrete( Setup.setupCollectorAndMockFor( collector, AnnotationTypeDeclaration.class, ANNOTATION_NAME ) );

        classVisitor.visit();

        assertEquals( 1, classVisitor.getValueMap().get( INTERFACE_NAME ).intValue() );
        assertEquals( 1, classVisitor.getValueMap().get( ABSTRACT_CLASS_NAME ).intValue() );
        assertEquals( 0, classVisitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 0, classVisitor.getValueMap().get( ENUM_NAME ).intValue() );
        assertEquals( 0, classVisitor.getValueMap().get( ANNOTATION_NAME ).intValue() );

        assertEquals( 5, getNumberOfClasses() );
    }
}
