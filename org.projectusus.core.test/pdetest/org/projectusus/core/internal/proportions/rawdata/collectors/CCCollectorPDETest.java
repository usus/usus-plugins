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


public class CCCollectorPDETest extends PDETestForMetricsComputation {

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
        createAndCompute( "1" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(1, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneWhile() throws Exception {
        createAndCompute( "_while" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneDo() throws Exception {
        createAndCompute( "_do" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneFor() throws Exception {
        createAndCompute( "_for" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneForeach() throws Exception {
        createAndCompute( "_foreach" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void twoIf() throws Exception {
        createAndCompute( "_if" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(3, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void threeSwitch() throws Exception {
        createAndCompute( "_switch" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(4, model.getOverallMetric( CodeProportionKind.CC ));
    }
    @Test
    public void oneCatch() throws Exception {
        createAndCompute( "_catch" );
        assertEquals( 1, UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals(1, model.getNumberOf(CodeProportionUnit.METHOD));
        assertEquals(2, model.getOverallMetric( CodeProportionKind.CC ));
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "CC" );
    }
}
