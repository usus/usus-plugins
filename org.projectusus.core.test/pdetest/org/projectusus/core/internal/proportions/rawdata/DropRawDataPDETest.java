package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IProject;
import org.junit.Test;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.UsusCorePlugin;

public class DropRawDataPDETest extends PDETestForMetricsComputation  {

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
        createFileAndBuild( "Reset" + "1" );

        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
        assertEquals( 1, ususModel.getNumberOf( project, CodeProportionUnit.CLASS ));
        assertEquals( 0, ususModel.getViolationCount( project, CodeProportionKind.KG ) );
        assertEquals( 2, ususModel.getNumberOf( project, CodeProportionUnit.METHOD ));
        assertEquals( 1, ususModel.getViolationCount( project, CodeProportionKind.ML ) );
        assertEquals( 1, ususModel.getViolationCount( project, CodeProportionKind.CC ) );
        assertEquals( 1, getClassCount() );
    }   
    
    private void computeFiles2AndCheckPreconditions() throws Exception {
        createFile( "Reset" + "2" );
        createFileAndBuild( "Reset" + "1" );

        IUsusModel ususModel = UsusCorePlugin.getUsusModel();
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
