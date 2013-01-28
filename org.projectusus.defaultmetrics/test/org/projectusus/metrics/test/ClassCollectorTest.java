package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.projectusus.metrics.util.Setup.setupCollectorAndMockFor;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.metrics.AbstractClassCollector;

public class ClassCollectorTest extends CollectorTestHelper {

    private static final String NAME = "Name";

    private AbstractClassCollector collector;
    private ClassCountVisitor visitor = new ClassCountVisitor();

    @Before
    public void setup() throws JavaModelException {
        ASTNodeHelper nodeHelper = setupNodeHelperForClass();

        UsusModelProvider.clear( nodeHelper );
        collector = new AbstractClassCollector();
    }

    @Test
    public void singleClassYields1() {
        collector.visit( setupCollectorAndMockFor( collector, TypeDeclaration.class, NAME ) );

        visitor.visit();

        assertEquals( 1, visitor.getClassCount() );
    }

    @Test
    public void singleAnnotationYields1() {
        collector.visit( setupCollectorAndMockFor( collector, AnnotationTypeDeclaration.class, NAME ) );

        visitor.visit();

        assertEquals( 1, visitor.getClassCount() );
    }

    @Test
    public void singleEnumYields1() {
        collector.visit( setupCollectorAndMockFor( collector, EnumDeclaration.class, NAME ) );

        visitor.visit();

        assertEquals( 1, visitor.getClassCount() );
    }

    @Test
    public void twoClassesAndAnnotationAndEnumYield4() {
        collector.visit( setupCollectorAndMockFor( collector, TypeDeclaration.class, NAME ) );
        collector.visit( setupCollectorAndMockFor( collector, TypeDeclaration.class, NAME ) );
        collector.visit( setupCollectorAndMockFor( collector, AnnotationTypeDeclaration.class, NAME ) );
        collector.visit( setupCollectorAndMockFor( collector, EnumDeclaration.class, NAME ) );

        visitor.visit();

        assertEquals( 4, visitor.getClassCount() );
    }

}
