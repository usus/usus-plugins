package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;
import org.projectusus.metrics.util.CyclomaticComplexityVisitor;

public class CCCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void emptyMethod() throws Exception {
        createFileAndBuild( "1" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getCC() );
    }

    @Test
    public void oneWhile() throws Exception {
        createFileAndBuild( "_while" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getCC() );
    }

    @Test
    public void oneDo() throws Exception {
        createFileAndBuild( "_do" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getCC() );
    }

    @Test
    public void oneFor() throws Exception {
        createFileAndBuild( "_for" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getCC() );
    }

    @Test
    public void oneForeach() throws Exception {
        createFileAndBuild( "_foreach" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getCC() );
    }

    @Test
    public void twoIf() throws Exception {
        createFileAndBuild( "_if" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 3, getCC() );
    }

    @Test
    public void threeSwitch() throws Exception {
        createFileAndBuild( "_switch" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 4, getCC() );
    }

    @Test
    public void oneCatch() throws Exception {
        createFileAndBuild( "_catch" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 2, getCC() );
    }

    @Test
    public void threeConditionals() throws Exception {
        createFileAndBuild( "_conditional" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 4, getCC() );
    }

    @Test
    public void allOperantsWithinConditionalAreCounted() throws Exception {
        createFileAndBuild( "_conditional_3operants" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 3, getCC() );
    }

    @Test
    public void twoLogicalsAndTwoBitwise() throws Exception {
        createFileAndBuild( "_logical" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, getNumberOfMethods() );
        assertEquals( 1, getCC() );
    }

    @Test
    public void bestScores2Methods() throws Exception {
        createFileAndBuild( "_bestScores2" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 3, getNumberOfMethods() );
        assertEquals( 5, getCC() );
    }

    @Test
    public void bestScores1Method() throws Exception {
        createFileAndBuild( "_bestScores1" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 2, getNumberOfMethods() );
        assertEquals( 4, getCC() );
    }

    protected IFile createFileAndBuild( String filenumber ) throws Exception {
        return super.createFileAndBuild( "CC" + filenumber );
    }

    private int getCC() {
        CyclomaticComplexityVisitor r = new CyclomaticComplexityVisitor();
        r.visit();
        return r.getCC();
    }
}
