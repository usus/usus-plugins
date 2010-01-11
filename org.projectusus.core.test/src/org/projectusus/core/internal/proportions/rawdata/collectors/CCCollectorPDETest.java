package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;


public class CCCollectorPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup() throws CoreException{
        WorkspaceRawData.getInstance().dropRawData( project );
        makeUsusProject( false );
        addJavaNature();
   }
    
    @Test
    public void emptyMethod() throws Exception {
        createAndCompute( "1" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneWhile() throws Exception {
        createAndCompute( "_while" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneDo() throws Exception {
        createAndCompute( "_do" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneFor() throws Exception {
        createAndCompute( "_for" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneForeach() throws Exception {
        createAndCompute( "_foreach" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void twoIf() throws Exception {
        createAndCompute( "_if" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(3, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void threeSwitch() throws Exception {
        createAndCompute( "_switch" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, classRD.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneCatch() throws Exception {
        createAndCompute( "_catch" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, classRD.getOverallMetric( CodeProportionKind.CC ));
    }

    private Set<ClassRawData> getClasses() {
        return WorkspaceRawData.getInstance().getAllClassRawData();
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "CC" );
    }
}
