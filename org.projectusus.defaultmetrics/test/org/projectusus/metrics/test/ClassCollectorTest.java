package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.projectusus.metrics.util.Setup.setupCollectorAndMockFor;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.metrics.ClassCollector;

public class ClassCollectorTest extends CollectorTestHelper {

    private static final String NAME = "Name";

    private ClassCollector collector;
    private ClassCountVisitor visitor = new ClassCountVisitor();

    @Before
    public void setup() throws JavaModelException {
        UsusModelProvider.clear( setupNodeHelperForClass() );
        collector = new ClassCollector();
    }

    @Test
    public void singleClassYields1() {
        collector.init( setupCollectorAndMockFor( collector, AbstractTypeDeclaration.class, NAME ) );

        visitor.visit();

        assertEquals( 1, visitor.getClassCount() );
    }

    @Test
    public void fourClassesYield4() {
        collector.init( setupCollectorAndMockFor( collector, AbstractTypeDeclaration.class, NAME ) );
        collector.init( setupCollectorAndMockFor( collector, AbstractTypeDeclaration.class, NAME ) );
        collector.init( setupCollectorAndMockFor( collector, AbstractTypeDeclaration.class, NAME ) );
        collector.init( setupCollectorAndMockFor( collector, AbstractTypeDeclaration.class, NAME ) );

        visitor.visit();

        assertEquals( 4, visitor.getClassCount() );
    }

}
