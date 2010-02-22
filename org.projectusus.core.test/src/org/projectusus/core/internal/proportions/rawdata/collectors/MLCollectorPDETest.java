package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;


public class MLCollectorPDETest extends PDETestForMetricsComputation {

    private IUsusModel model;
    
    @Before
    public void setup() throws CoreException{
        UsusCorePlugin.getUsusModelWriteAccess().dropRawData( project );
        makeUsusProject( false );
        addJavaNature();
        model = UsusCorePlugin.getUsusModel();
   }
    
    @Test
    public void emptyMethod() throws Exception {
        createAndCompute( "_empty" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(0, model.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneAssignment() throws Exception {
        createAndCompute( "_assign" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneIfThenElse() throws Exception {
        createAndCompute( "_ite" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneIfThenElseWithAssignments() throws Exception {
        createAndCompute( "_iteassign" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(3, model.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneInitializer() throws Exception {
        createAndCompute( "_init" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneStaticInitializer() throws Exception {
        createAndCompute( "_staticinit" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneAnonymous() throws Exception {
        createAndCompute( "_anon" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void oneAnonymous3after() throws Exception {
        createAndCompute( "_anon3after" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, model.getOverallMetric( CodeProportionKind.ML ));
    }
    @Test
    public void oneAnonymous3before() throws Exception {
        createAndCompute( "_anon3before" );
        assertEquals( 1, getClassesCount() );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, model.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void oneAnonymousWithMethod() throws Exception {
        createAndCompute( "_anonWmeth" );
        assertEquals( 1, getClassesCount() );
        assertEquals(2, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void oneAnonymousWithMethod3after() throws Exception {
        createAndCompute( "_anonWmeth3after" );
        assertEquals( 1, getClassesCount() );
        assertEquals(2, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, model.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneAnonymousWithMethod3before() throws Exception {
        createAndCompute( "_anonWmeth3before" );
        assertEquals( 1, getClassesCount() );
        assertEquals(2, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, model.getOverallMetric( CodeProportionKind.ML ));
    }
    
    @Test
    public void oneAnonymousWith4Methods() throws Exception {
        createAndCompute( "_anonW4meth" );
        assertEquals( 1, getClassesCount() );
        assertEquals(5, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(7, model.getOverallMetric( CodeProportionKind.ML ));
    }

    @Test
    public void twoClassesWithOneAssignment() throws Exception {
        createAndCompute( "_2assign" );
        assertEquals( 2, getClassesCount() );
        assertEquals(2, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, model.getOverallMetric( CodeProportionKind.ML ));
    }

    private int getClassesCount() {
        return UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS );
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "ML" );
    }
}
