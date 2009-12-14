package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.ReflectionUtil;
import org.projectusus.core.internal.proportions.rawdata.AcdModel;
import org.projectusus.core.internal.proportions.rawdata.AdjacencyNode;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;


public class ACDCollectorPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup(){
        ClassRawData.resetAcdModel();
    }
    
    @Test
    public void singleClass() throws Exception {
        IFile file = createWSFile( "Acd1.java", loadContent("Acd1.test") );
        computeFile(file);
        assertEquals( 1, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoUnrelatedClasses() throws Exception {
        IFile file = createWSFile( "Acd2.java", loadContent("Acd2.test") );
        computeFile(file);
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.5, getACD(), 0.0001 );
    }
    
    @Test
    public void twoRelatedClasses1knows2() throws Exception {
        addJavaNature();
        IFile file = createWSFile( "Acd2.java", loadContent("Acd3_1.test") );
        computeFile(file);
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses2know2() throws Exception {
        IFile file = createWSFile( "Acd3_2.java", loadContent("Acd3_2.test") );
        computeFile(file);
        assertEquals( 2, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void threeUnrelatedClasses() throws Exception {
        IFile file = createWSFile( "Acd4.java", loadContent("Acd4.test") );
        computeFile(file);
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 0.3333, getACD(), 0.0001 );
    }
    
    @Test
    public void threeRelatedClasses1knows2() throws Exception {
        IFile file = createWSFile( "Acd5.java", loadContent("Acd5.test") );
        computeFile(file);
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 0.6666, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses2know2() throws Exception {
        IFile file = createWSFile( "Acd6.java", loadContent("Acd6.test") );
        computeFile(file);
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 0.8333, getACD(), 0.0001 );
    }
    
    @Test
    public void threeRelatedClasses3know2() throws Exception {
        IFile file = createWSFile( "Acd7.java", loadContent("Acd7.test") );
        computeFile(file);
        assertEquals( 3, getAdjacencyList().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void tenRelatedClasses1knows2() throws Exception {
        IFile file = createWSFile( "Acd8.java", loadContent("Acd8.test") );
        computeFile(file);
        assertEquals( 10, getAdjacencyList().size() );
        assertEquals( 0.11, getACD(), 0.0001 );
    }
    
    @Test
    public void tenRelatedClasses1knows2know2() throws Exception {
        IFile file = createWSFile( "Acd9.java", loadContent("Acd9.test") );
        computeFile(file);
        assertEquals( 10, getAdjacencyList().size() );
        assertEquals( 0.2, getACD(), 0.0001 );
    }
    
    private double getACD() {
        return ClassRawData.getAcdModel().getRelativeACD();
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
