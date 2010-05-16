package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class CCCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void emptyMethod() throws Exception {
        createFileAndBuild( "1" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 1, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void oneWhile() throws Exception {
        createFileAndBuild( "_while" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 2, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void oneDo() throws Exception {
        createFileAndBuild( "_do" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 2, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void oneFor() throws Exception {
        createFileAndBuild( "_for" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 2, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void oneForeach() throws Exception {
        createFileAndBuild( "_foreach" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 2, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void twoIf() throws Exception {
        createFileAndBuild( "_if" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 3, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void threeSwitch() throws Exception {
        createFileAndBuild( "_switch" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 4, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    @Test
    public void oneCatch() throws Exception {
        createFileAndBuild( "_catch" );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, getModel().getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 2, getModel().getOverallMetric( CodeProportionKind.CC ) );
    }

    protected IFile createFileAndBuild( String filenumber ) throws Exception {
        return super.createFileAndBuild( "CC" + filenumber );
    }
}
