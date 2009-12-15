package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.ReflectionUtil;
import org.projectusus.core.internal.proportions.rawdata.AcdModel;
import org.projectusus.core.internal.proportions.rawdata.AdjacencyNode;
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
    
    private double getACD() {
        return ClassRawData.getAcdModel().getRelativeACD();
    }

    private void createAndCompute( String filenumber ) throws CoreException, Exception {
        IFile file = createWSFile( "Acd"+filenumber +".java", loadContent("Acd"+filenumber+".test") );
        computeFile(file);
    }

    @SuppressWarnings( "unchecked" )
    private List<AdjacencyNode> getAdjacencyList() {
        AcdModel acdModel = ClassRawData.getAcdModel();
        try {
            return (List<AdjacencyNode>)ReflectionUtil.getValue( acdModel, "classes" );
        } catch( Throwable e ) {
            // nothing
        }
        return null;
    }

    
}
