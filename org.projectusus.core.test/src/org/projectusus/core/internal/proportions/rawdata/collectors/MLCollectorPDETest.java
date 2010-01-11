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


public class MLCollectorPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup() throws CoreException{
        WorkspaceRawData.getInstance().dropRawData( project );
        makeUsusProject( false );
        addJavaNature();
   }
    
    @Test
    public void emptyMethod() throws Exception {
        createAndCompute( "_empty" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(0, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneAssignment() throws Exception {
        createAndCompute( "_assign" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneIfThenElse() throws Exception {
        createAndCompute( "_ite" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneIfThenElseWithAssignments() throws Exception {
        createAndCompute( "_iteassign" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(3, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneInitializer() throws Exception {
        createAndCompute( "_init" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneStaticInitializer() throws Exception {
        createAndCompute( "_staticinit" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneAnonymous() throws Exception {
        createAndCompute( "_anon" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void oneAnonymous3after() throws Exception {
        createAndCompute( "_anon3after" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneAnonymous3before() throws Exception {
        createAndCompute( "_anon3before" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, classRD.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void oneAnonymousWithMethod() throws Exception {
        createAndCompute( "_anonWmeth" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(2, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void oneAnonymousWithMethod3after() throws Exception {
        createAndCompute( "_anonWmeth3after" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(2, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneAnonymousWithMethod3before() throws Exception {
        createAndCompute( "_anonWmeth3before" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(2, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, classRD.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneAnonymousWith4Methods() throws Exception {
        createAndCompute( "_anonW4meth" );
        assertEquals( 1, getClasses().size() );
        ClassRawData classRD = getClasses().iterator().next();
        assertEquals(5, classRD.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(7, classRD.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void twoClassesWithOneAssignment() throws Exception {
        createAndCompute( "_2assign" );
        assertEquals( 2, getClasses().size() );
        for(ClassRawData classRD : getClasses()){
            assertEquals(1, classRD.getNumberOf(CodeProportionUnit.METHOD));
            assertEquals(1, classRD.getOverallMetric( CodeProportionKind.ML ));
        }
    }


    private Set<ClassRawData> getClasses() {
        return WorkspaceRawData.getInstance().getAllClassRawData();
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "ML" );
    }
}
