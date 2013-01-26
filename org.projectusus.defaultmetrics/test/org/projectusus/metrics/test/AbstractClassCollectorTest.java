package org.projectusus.metrics.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.metrics.util.CountingUtils.getNumberOfClasses;
import static org.projectusus.metrics.util.TypeBindingMocker.createTypeBinding;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.AbstractClassCollector;
import org.projectusus.metrics.util.ClassValueVisitor;
import org.projectusus.metrics.util.Setup;

public class AbstractClassCollectorTest {

    private static final String ABSTRACT_CLASS_NAME = "AbstractClassName";
    private static final String ENUM_NAME = "EnumName";
    private static final String INTERFACE_NAME = "InterfaceName";
    private static final String ANNOTATION_NAME = "AnnotationName";
    private static final String CLASS_NAME = "ClassName";

    private AbstractClassCollector collector;
    private ClassValueVisitor visitor = new ClassValueVisitor( MetricsResults.ABSTRACTNESS );

    @Before
    public void setup() throws JavaModelException {
        ASTNodeHelper nodeHelper = mock( ASTNodeHelper.class );
        ITypeBinding typeBinding = createTypeBinding();
        when( nodeHelper.resolveBindingOf( org.mockito.Matchers.any( AbstractTypeDeclaration.class ) ) ).thenReturn( typeBinding );

        UsusModelProvider.clear( nodeHelper );
        collector = new AbstractClassCollector();
    }

    @Test
    public void interfaceIsMarkedAsAbstract() {
        TypeDeclaration node = Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME );
        when( Boolean.valueOf( node.isInterface() ) ).thenReturn( Boolean.TRUE );

        collector.visit( node );

        visitor.visit();

        assertEquals( 1, visitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void abstractClassIsMarkedAsAbstract() {
        TypeDeclaration node = Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME );
        when( Integer.valueOf( node.getModifiers() ) ).thenReturn( Integer.valueOf( Modifier.ABSTRACT ) );

        collector.visit( node );

        visitor.visit();

        assertEquals( 1, visitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void concreteClassIsNotMarkedAsAbstract() {
        collector.visit( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME ) );

        visitor.visit();

        assertEquals( 0, visitor.getValueMap().get( CLASS_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void enumIsNotMarkedAsAbstract() {

        collector.visit( Setup.setupCollectorAndMockFor( collector, EnumDeclaration.class, ENUM_NAME ) );

        visitor.visit();

        assertEquals( 0, visitor.getValueMap().get( ENUM_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void annotationClassIsNotMarkedAsAbstract() {
        collector.visit( Setup.setupCollectorAndMockFor( collector, AnnotationTypeDeclaration.class, ANNOTATION_NAME ) );

        visitor.visit();

        assertEquals( 0, visitor.getValueMap().get( ANNOTATION_NAME ).intValue() );
        assertEquals( 1, getNumberOfClasses() );
    }

    @Test
    public void abstractnessOfMultipleClassesIsAdditive() {
        TypeDeclaration interfaceNode = Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, INTERFACE_NAME );
        when( Boolean.valueOf( interfaceNode.isInterface() ) ).thenReturn( Boolean.TRUE );
        collector.visit( interfaceNode );

        TypeDeclaration abstractNode = Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, ABSTRACT_CLASS_NAME );
        when( Integer.valueOf( abstractNode.getModifiers() ) ).thenReturn( Integer.valueOf( Modifier.ABSTRACT ) );
        collector.visit( abstractNode );

        collector.visit( Setup.setupCollectorAndMockFor( collector, TypeDeclaration.class, CLASS_NAME ) );

        collector.visit( Setup.setupCollectorAndMockFor( collector, EnumDeclaration.class, ENUM_NAME ) );

        collector.visit( Setup.setupCollectorAndMockFor( collector, AnnotationTypeDeclaration.class, ANNOTATION_NAME ) );

        visitor.visit();

        assertEquals( 1, visitor.getValueMap().get( INTERFACE_NAME ).intValue() );
        assertEquals( 1, visitor.getValueMap().get( ABSTRACT_CLASS_NAME ).intValue() );

        assertEquals( 5, getNumberOfClasses() );
    }
}
