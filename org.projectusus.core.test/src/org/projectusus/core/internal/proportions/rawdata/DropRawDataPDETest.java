package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;

public class DropRawDataPDETest extends PDETestForMetricsComputation  {

    @Before
    public void setup() throws CoreException{
        UsusCorePlugin.getUsusModelWriteAccess().dropRawData( project );
        makeUsusProject( false );
        addJavaNature();
   }

    @Test
    public void dropWorkspaceWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        
        UsusCorePlugin.getUsusModelWriteAccess().dropAllRawData();
        
        checkProjectRawDataIsEmpty1File( project );
   }
    
    @Test
    public void dropProjectWithFile1() throws Exception {
        computeFile1AndCheckPreconditions();
        
        UsusCorePlugin.getUsusModelWriteAccess().dropRawData(project);
        
        checkProjectRawDataIsEmpty1File( project );
   }

    @Test
    public void dropProjectWithFiles2() throws Exception {
        computeFiles2AndCheckPreconditions();
        
        UsusCorePlugin.getUsusModelWriteAccess().dropRawData(project);
        
        checkProjectRawDataIsEmpty2Files( project );
   }
    
    private void checkProjectRawDataIsEmpty1File( IProject project ) {
        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
        assertEquals( 0, ususModel.getNumberOf( project, CodeProportionUnit.CLASS ));
        assertEquals( 0, ususModel.getNumberOf( project, CodeProportionUnit.METHOD ));
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 0, getClassCount() );
    }

    private void checkProjectRawDataIsEmpty2Files( IProject project ) {
        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
        assertEquals( 0, ususModel.getNumberOf( project, CodeProportionUnit.CLASS ));
        assertEquals( 0, ususModel.getNumberOf( project, CodeProportionUnit.METHOD ));
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 0, getClassCount() );
    }
    
    private void computeFile1AndCheckPreconditions() throws Exception {
        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
        createAndCompute( "1", "Reset" );
        assertEquals( 1, ususModel.getNumberOf( project, CodeProportionUnit.CLASS ));
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 2, ususModel.getNumberOf( project, CodeProportionUnit.METHOD ));
        assertEquals( 1, ususModel.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, ususModel.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 1, getClassCount() );
    }   
    
    private void computeFiles2AndCheckPreconditions() throws Exception {
        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
        IFile file2 = createWSFile( "Reset2.java", loadContent("Reset2.test") );
        createAndCompute( "1", "Reset" );
        computeJavaFile(file2);
        assertEquals( 2, ususModel.getNumberOf( project, CodeProportionUnit.CLASS ));
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 3, ususModel.getNumberOf( project, CodeProportionUnit.METHOD ));
        assertEquals( 1, ususModel.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, ususModel.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 2, getClassCount() );
    }   
    
    private int getClassCount() {
        return  UsusCorePlugin.getUsusModel().getNumberOf( CodeProportionUnit.CLASS );
    }
}
