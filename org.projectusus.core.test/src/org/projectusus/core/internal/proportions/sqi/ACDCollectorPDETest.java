package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;


public class ACDCollectorPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup() throws CoreException{
        WorkspaceRawData.getInstance().dropRawData( project );
        makeUsusProject( false );
        ClassRawData.resetAcdModel();
        addJavaNature();
   }
    
    @Test
    public void singleClass() throws Exception {
        createAndCompute( "1" );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoUnrelatedClasses() throws Exception {
        createAndCompute( "2" );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.5, getACD(), 0.0001 );
    }
    
    @Test
    public void twoRelatedClasses1knows2() throws Exception {
        createAndCompute( "3_1" );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses2know2() throws Exception {
        createAndCompute( "3_2" );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void threeUnrelatedClasses() throws Exception {
        createAndCompute( "4" );
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 0.3333, getACD(), 0.0001 );
    }
    
    @Test
    public void threeRelatedClasses1knows2() throws Exception {
        createAndCompute( "5" );
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 4/9.0, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses2know2() throws Exception {
        createAndCompute( "6" );
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 5/9.0, getACD(), 0.0001 );
    }
    
    @Test
    public void threeRelatedClasses3know2() throws Exception {
        createAndCompute( "7" );
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 7/9.0, getACD(), 0.0001 );
    }
    
    @Test
    public void tenRelatedClasses1knows2() throws Exception {
        createAndCompute( "8" );
        assertEquals( 10, getAdjacencyList().size() );
        assertEquals( 0.11, getACD(), 0.0001 );
    }
    
    @Test
    public void tenRelatedClasses1knows2know2() throws Exception {
        createAndCompute( "9" );
        assertEquals( 10, getAdjacencyList().size() );
        assertEquals( 0.2, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFilesTheSecondKnowsTheFirst() throws Exception {
        IFile firstFile = createWSFile( "Acd11a.java", loadContent("Acd11a.test") );
        createAndCompute("11b");
        computeFile( firstFile );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void x_twoFiles_TheFirstKnowsTheSecond_SecondIsDeleted() throws Exception {
        IFile firstFile = createWSFile( "Acd11a.java", loadContent("Acd11a.test") );
        IFile secondFile = createAndCompute("11b");
        computeFile( firstFile );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        WorkspaceRawData.getInstance().dropRawData( secondFile );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItKnowsAnotherWhichIsMissing() throws Exception {
        createAndCompute( "11a" );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void x_oneFile_ItKnowsAnotherWhichIsCreatedLater() throws Exception {
        createAndCompute( "11a" );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createAndCompute("11b");
        // dieses Problem muss man durch geeignete Change-Sets loesen -> Leif fragen
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void oneFile_ItIsKnownByAnotherWhichIsCreatedLater() throws Exception {
        createAndCompute( "11b" );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createAndCompute("11a");
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFiles_TheFirstKnowsTheSecond_FirstIsDeleted() throws Exception {
        IFile firstFile = createWSFile( "Acd11a.java", loadContent("Acd11a.test") );
        createAndCompute("11b");
        computeFile( firstFile );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        WorkspaceRawData.getInstance().dropRawData( firstFile );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFilesKnowEachOther() throws Exception {
        IFile file = createWSFile( "Acd10a.java", loadContent("Acd10a.test") );
        createAndCompute("10b");
        computeFile( file );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void x_twoFilesKnowEachOtherTheSecondIsDeleted() throws Exception {
        IFile firstFile = createWSFile( "Acd10a.java", loadContent("Acd10a.test") );
        IFile secondFile = createAndCompute("10b");
        computeFile( firstFile );
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        WorkspaceRawData.getInstance().dropRawData( secondFile );
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    private double getACD() {
        return ClassRawData.getAcdModel().getRelativeACD();
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "Acd" );
    }
}
