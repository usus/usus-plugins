package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.sqi.PDETestForMetricsComputation;

public class ResetRawDataPDETest extends PDETestForMetricsComputation  {

    private IProjectRawData projectRawData;

    @Before
    public void setup() throws CoreException{
        WorkspaceRawData.getInstance().dropRawData( project );
        makeUsusProject( false );
        addJavaNature();
   }

    @Test
    public void resetWorkspaceWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        
        WorkspaceRawData.getInstance().resetRawData(project);
        
        checkProjectRawDataIsEmpty1File( projectRawData );
   }
    
    @Test
    public void resetProjectWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        
        projectRawData.resetRawData();
        
        checkProjectRawDataIsEmpty1File( projectRawData );
   }

    @Test
    public void resetProjectWithFiles2() throws Exception {
        computeFiles2AndCheckPreconditions();
        
        projectRawData.resetRawData();
        
        checkProjectRawDataIsEmpty2Files( projectRawData );
   }
    
    private void checkProjectRawDataIsEmpty1File( IProjectRawData projectRawData ) {
        assertEquals( 1, projectRawData.getNumberOf( CodeProportionUnit.CLASS ));
        assertEquals( 2, projectRawData.getNumberOf( CodeProportionUnit.METHOD ));
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.KG ) );
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.ML ) );
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.CC ) );
//        TODO assertEquals( 1, getAdjacencyList().size() );
    }

    private void checkProjectRawDataIsEmpty2Files( IProjectRawData projectRawData ) {
        assertEquals( 2, projectRawData.getNumberOf( CodeProportionUnit.CLASS ));
        assertEquals( 3, projectRawData.getNumberOf( CodeProportionUnit.METHOD ));
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.KG ) );
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.ML ) );
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.CC ) );
//        TODO assertEquals( 2, getAdjacencyList().size() );
    }
    
    private void computeFile1AndCheckPreconditions() throws Exception {
        IFile file = createAndCompute( "1", "Reset" );
        projectRawData = JDTSupport.getProjectRawDataFor( file );
        assertEquals( 1, projectRawData.getNumberOf( CodeProportionUnit.CLASS ));
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.KG ) );
        assertEquals( 2, projectRawData.getNumberOf( CodeProportionUnit.METHOD ));
        assertEquals( 1, projectRawData.getViolationCount( CodeProportionKind.ML ) );
        assertEquals( 1, projectRawData.getViolationCount( CodeProportionKind.CC ) );
        assertEquals( 1, getAdjacencyList().size() );
    }   
    
    private void computeFiles2AndCheckPreconditions() throws Exception {
        IFile file2 = createWSFile( "Reset2.java", loadContent("Reset2.test") );
        IFile file1 = createAndCompute( "1", "Reset" );
        computeFile(file2);
        projectRawData = JDTSupport.getProjectRawDataFor( file1 );
        assertEquals( 2, projectRawData.getNumberOf( CodeProportionUnit.CLASS ));
        assertEquals( 0, projectRawData.getViolationCount( CodeProportionKind.KG ) );
        assertEquals( 3, projectRawData.getNumberOf( CodeProportionUnit.METHOD ));
        assertEquals( 1, projectRawData.getViolationCount( CodeProportionKind.ML ) );
        assertEquals( 1, projectRawData.getViolationCount( CodeProportionKind.CC ) );
        assertEquals( 2, getAdjacencyList().size() );
    }   
}
