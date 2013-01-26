package org.projectusus.core.proportions.rawdata.jdtdriver.publicfields;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.Modifier;
import org.junit.Test;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

import com.google.common.collect.Multimap;

public class PublicFieldPDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsRegardingAbstractAndConcreteTypesAreValid() throws Exception {
        project.createFolder( "publicfields" );
        IFile file = createJavaFile( "publicfields/PublicFields.java" );

        PublicFieldInspector inspector = new PublicFieldInspector();

        new JavaFileDriver( file ).compute( createSetWith( inspector ) );

        Multimap<String, Integer> map = inspector.getMap();

        validateEmptyClass( map.get( "EmptyClass" ) );
        validateOneOfEachKind( map.get( "OneOfEachKind" ) );
        validateOneOfEachKind( map.get( "OneOfEachKindTwice" ) );
        validateOneOfEachKindExceptE( map.get( "AnotherClassWithFields" ) );
        validateOneOfEachKind( map.get( "OneOfEachKindTwoClasses" ) );
        validateEmptyClass( map.get( "AnotherClassWithoutFields" ) );

        validateOnePublicField( map.get( "OnePublicField" ) );
        validateOnlyPublicStaticFinal( map.get( "OnlyPublicStaticFinal" ) );
    }

    private void validateEmptyClass( Collection<Integer> collection ) {
        assertThat( collection.size(), is( 0 ) );
    }

    private void validateOnePublicField( Collection<Integer> collection ) {
        assertThat( collection.size(), is( 1 ) );
        Iterator<Integer> iterator = collection.iterator();
        assertIsPublicStaticFinal( iterator.next(), true, false, false );
    }

    private void validateOnlyPublicStaticFinal( Collection<Integer> collection ) {
        assertThat( collection.size(), is( 4 ) );
        Iterator<Integer> iterator = collection.iterator();
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), true, true, true );
    }

    private void validateOneOfEachKind( Collection<Integer> collection ) {
        assertThat( collection.size(), is( 7 ) );
        Iterator<Integer> iterator = collection.iterator();
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), true, false, false );
        assertIsPublicStaticFinal( iterator.next(), true, true, false );
        assertIsPublicStaticFinal( iterator.next(), true, false, true );
        assertIsPublicStaticFinal( iterator.next(), true, true, true );
    }

    private void validateOneOfEachKindExceptE( Collection<Integer> collection ) {
        assertThat( collection.size(), is( 6 ) );
        Iterator<Integer> iterator = collection.iterator();
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), false, false, false );
        assertIsPublicStaticFinal( iterator.next(), true, false, false );
        assertIsPublicStaticFinal( iterator.next(), true, false, true );
        assertIsPublicStaticFinal( iterator.next(), true, true, true );
    }

    private void assertIsPublicStaticFinal( Integer field, boolean isPublic, boolean isStatic, boolean isFinal ) {
        assertThat( isPublic( field ), is( isPublic ) );
        assertThat( isStatic( field ), is( isStatic ) );
        assertThat( isFinal( field ), is( isFinal ) );
    }

    private boolean isStatic( int fieldModifiers ) {
        return contains( fieldModifiers, Modifier.STATIC );
    }

    private boolean isFinal( int fieldModifiers ) {
        return contains( fieldModifiers, Modifier.FINAL );
    }

    private boolean isPublic( int fieldModifiers ) {
        return contains( fieldModifiers, Modifier.PUBLIC );
    }

    private boolean contains( int fieldModifiers, int flag ) {
        return (fieldModifiers & flag) != 0;
    }

    private Set<MetricsCollector> createSetWith( MetricsCollector inspector ) {
        Set<MetricsCollector> set = new HashSet<MetricsCollector>();
        set.add( inspector );
        return set;
    }
}
