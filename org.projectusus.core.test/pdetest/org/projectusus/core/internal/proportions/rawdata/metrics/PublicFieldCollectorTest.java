package org.projectusus.core.internal.proportions.rawdata.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;
import org.projectusus.core.statistics.DefaultCockpitExtension;
import org.projectusus.statistics.PublicFieldStatistic;

public class PublicFieldCollectorTest extends PDETestForMetricsComputation {

    private DefaultCockpitExtension statistic = new PublicFieldStatistic();

    @Before
    public void createWorkspaceFolder() throws CoreException {
        createWSFolder( "publicfields", project1 );
    }

    @Test
    public void countNoPublicFields() throws Exception {
        buildFullyAndWait();
        visitAndCheck( 0, 0 );
    }

    @Test
    public void countPublicFields() throws Exception {
        createFileAndBuild( "OneOfEachKind" );
        visitAndCheck( 1, 1 );
    }

    @Test
    public void countPublicFieldsWithInnerClass() throws Exception {
        createFileAndBuild( "OneOfEachKindTwoClasses" );
        visitAndCheck( 2, 1 );
        assertEquals( 1, statistic.getHotspots().size() );
    }

    @Test
    public void countPublicFieldsWithInnerClassAlsoViolating() throws Exception {
        createFileAndBuild( "OneOfEachKindTwice" );
        visitAndCheck( 2, 2 );
        assertEquals( 2, statistic.getHotspots().size() );
    }

    protected IFile createFileAndBuild( String string ) throws CoreException, Exception {
        IFile file = createJavaWSFile( "publicfields/" + string + ".java" );
        buildFullyAndWait();
        return file;
    }

    private void visitAndCheck( int numberOfClasses, int expected ) {
        assertEquals( numberOfClasses, getNumberOfClasses() );
        statistic.visit();
        assertEquals( expected, statistic.getMetricsSum() );
    }

    // private DefaultCockpitExtension getClassValidator() {
    // return new DefaultCockpitExtension( "", 0 ) {
    // @Override
    // public void inspectClass( SourceCodeLocation location, MetricsResults results ) {
    // addViolation( location, results.get( PublicFieldCollector.PUBLIC_FIELDS ).intValue() );
    // }
    //
    // @Override
    // protected String getDescription() {
    // return null;
    // }
    // };
    // }
}
