package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.metrics.util.Setup.setupCollectorAndMockFor;
import static org.projectusus.metrics.util.TypeBindingMocker.createTypeBinding;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.metrics.AbstractClassCollector;

public class ClassCollectorTest {

    private static final String NAME = "Name";

    private AbstractClassCollector collector;
    private ClassCountVisitor visitor = new ClassCountVisitor();

    @Before
    public void setup() throws JavaModelException {
        ASTNodeHelper nodeHelper = mock( ASTNodeHelper.class );
        ITypeBinding typeBinding = createTypeBinding();
        when( nodeHelper.resolveBindingOf( org.mockito.Matchers.any( AbstractTypeDeclaration.class ) ) ).thenReturn( typeBinding );

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
