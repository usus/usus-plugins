package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;


public class ClassCollectorPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup() throws CoreException{
        UsusCorePlugin.getUsusModel().dropRawData( project );
        makeUsusProject( false );
        addJavaNature();
   }
    
    @Test
    public void oneClass() throws Exception {
        createAndCompute( "_one" );
        assertEquals( 1, getClasses().size() );
    }
    
    @Test
    public void twoClasses() throws Exception {
        createAndCompute( "_two" );
        assertEquals( 2, getClasses().size() );
    }

    @Test
    public void oneWithInnerClass() throws Exception {
        createAndCompute( "_inner" );
        assertEquals( 2, getClasses().size() );
    }

    @Test
    public void oneWithStaticInnerClass() throws Exception {
        createAndCompute( "_static" );
        assertEquals( 2, getClasses().size() );
    }
    
    @Test
    public void oneWithAnonymousClass() throws Exception {
        createAndCompute( "_anon" );
        assertEquals( 2, getClasses().size() );
    }
    
    private Set<ClassRawData> getClasses() {
        return UsusCorePlugin.getUsusModel().getAllClassRawData();
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "Class" );
    }
}
