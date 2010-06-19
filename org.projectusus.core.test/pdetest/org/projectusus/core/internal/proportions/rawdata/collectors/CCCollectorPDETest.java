package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.CyclomaticComplexityStatistic;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class CCCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void emptyMethod() throws Exception {
        createFileAndBuild( "1" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMetricsSum() );
    }

    @Test
    public void oneWhile() throws Exception {
        createFileAndBuild( "_while" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getMetricsSum() );
    }

    @Test
    public void oneDo() throws Exception {
        createFileAndBuild( "_do" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getMetricsSum() );
    }

    @Test
    public void oneFor() throws Exception {
        createFileAndBuild( "_for" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getMetricsSum() );
    }

    @Test
    public void oneForeach() throws Exception {
        createFileAndBuild( "_foreach" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getMetricsSum() );
    }

    @Test
    public void twoIf() throws Exception {
        createFileAndBuild( "_if" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 3, getMetricsSum() );
    }

    @Test
    public void threeSwitch() throws Exception {
        createFileAndBuild( "_switch" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 4, getMetricsSum() );
    }

    @Test
    public void oneCatch() throws Exception {
        createFileAndBuild( "_catch" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getMetricsSum() );
    }

    @Test
    public void threeConditionals() throws Exception {
        createFileAndBuild( "_conditional" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 4, getMetricsSum() );
    }

    @Test
    public void twoLogicalsAndTwoBitwise() throws Exception {
        createFileAndBuild( "_logical" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getMetricsSum() );
    }

    @Test
    public void bestScores2Methods() throws Exception {
        createFileAndBuild( "_bestScores2" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 3, getNumberOfMethods() );
        assertEquals( 5, getMetricsSum() );
    }

    @Test
    public void bestScores1Method() throws Exception {
        createFileAndBuild( "_bestScores1" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 2, getNumberOfMethods() );
        assertEquals( 4, getMetricsSum() );
    }

    protected IFile createFileAndBuild( String filenumber ) throws Exception {
        return super.createFileAndBuild( "CC" + filenumber );
    }

    private int getMetricsSum() {
        return new CyclomaticComplexityStatistic().getViolationSum();
    }
}
