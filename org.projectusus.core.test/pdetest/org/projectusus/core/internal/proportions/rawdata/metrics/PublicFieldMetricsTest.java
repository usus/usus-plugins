package org.projectusus.core.internal.proportions.rawdata.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.ValidatorFactory;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class PublicFieldMetricsTest extends PDETestForMetricsComputation {

    private DefaultCockpitExtension statistic;

    @Before
    public void setup() throws Exception {
        statistic = ValidatorFactory.getClassValidator( "public fields" );
        createWSFolder( "publicfields", project1 );
    }

    @Test
    public void countNoPublicFields() throws Exception {
        createJavaFileAndBuild( "publicfields/EmptyClass" );
        checkNumberOfClassesAndOfPublicFields( 1, 0 );
    }

    @Test
    public void countOnePublicField() throws Exception {
        createJavaFileAndBuild( "publicfields/OnePublicField" );
        checkNumberOfClassesAndOfPublicFields( 1, 1 );
    }

    @Test
    public void countPublicStaticFinalFields() throws Exception {
        createJavaFileAndBuild( "publicfields/OnlyPublicStaticFinal" );
        checkNumberOfClassesAndOfPublicFields( 1, 0 );
    }

    @Test
    public void countPublicFields() throws Exception {
        createJavaFileAndBuild( "publicfields/OneOfEachKind" );
        checkNumberOfClassesAndOfPublicFields( 1, 3 );
    }

    @Test
    public void countPublicFieldsWithInnerClass() throws Exception {
        createJavaFileAndBuild( "publicfields/OneOfEachKindTwoClasses" );
        checkNumberOfClassesAndOfPublicFields( 2, 3 );
        assertEquals( 1, statistic.getHotspots().size() );
    }

    @Test
    public void countPublicFieldsWithInnerClassAlsoViolating() throws Exception {
        createJavaFileAndBuild( "publicfields/OneOfEachKindTwice" );
        checkNumberOfClassesAndOfPublicFields( 2, 5 );
        assertEquals( 2, statistic.getHotspots().size() );
    }

    private void checkNumberOfClassesAndOfPublicFields( int numberOfClasses, int expected ) {
        assertEquals( numberOfClasses, getNumberOfClasses() );
        statistic.visit();
        assertEquals( expected, statistic.getMetricsSum() );
    }
}
