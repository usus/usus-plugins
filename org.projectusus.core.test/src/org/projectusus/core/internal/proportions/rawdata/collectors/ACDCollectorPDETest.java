package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.AcdSQIComputer;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;


public class ACDCollectorPDETest extends PDETestForMetricsComputation {

    private IUsusModel model;
    private IUsusModelWriteAccess writeModel;

    @Before
    public void setup() throws CoreException{
        writeModel = UsusCorePlugin.getUsusModelWriteAccess();
//        writeModel.dropAllRawData();
        writeModel.dropRawData( project );
//        makeUsusProject( false );
        addJavaNature();
        model = UsusCorePlugin.getUsusModel();
   }
    
    @Test
    public void singleClass() throws Exception {
        createAndCompute( "1" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoUnrelatedClasses() throws Exception {
        createAndCompute( "2" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.5, getACD(), 0.0001 );
    }
    
    @Test
    public void twoRelatedClasses1knows2() throws Exception {
        createAndCompute( "3_1" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClassesKnowEachOther() throws Exception {
        createAndCompute( "_twoKnowEachOther" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
        // TODO NR
//        assertEquals( 2, model.getSumOfAllDirectChildrenOfAllClasses() ); 
//        assertEquals( 4, model.getSumOfAllKnownChildrenOfAllClasses() );
    }
    
    @Test
    public void twoRelatedClasses1knows2Statically() throws Exception {
        createAndCompute( "_1knows2static" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void twoRelatedClasses1knows2Generic() throws Exception {
        createAndCompute( "_1knows2generic" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void twoRelatedClasses1knows2InGenericArgument() throws Exception {
        createAndCompute( "_1knows2InGenericArg" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void twoRelatedClasses2know2() throws Exception {
        createAndCompute( "3_2" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void threeUnrelatedClasses() throws Exception {
        createAndCompute( "4" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.3333, getACD(), 0.0001 );
    }
    
    @Test
    public void threeRelatedClasses1knows2() throws Exception {
        createAndCompute( "5" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 4/9.0, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses2know2() throws Exception {
        createAndCompute( "6" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 5/9.0, getACD(), 0.0001 );
    }
    
    @Test
    public void threeRelatedClasses3know2() throws Exception {
        createAndCompute( "7" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 7/9.0, getACD(), 0.0001 );
    }
    
    @Test
    public void tenRelatedClasses1knows2() throws Exception {
        createAndCompute( "8" );
        assertEquals( 10, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.11, getACD(), 0.0001 );
    }
    
    @Test
    public void tenRelatedClasses1knows2know2() throws Exception {
        createAndCompute( "9" );
        assertEquals( 10, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.2, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFilesTheSecondKnowsTheFirst() throws Exception {
        IFile firstFile = createWSFile( "Acd11a.java", loadContent("Acd11a.test") );
        createAndCompute("11b");
        computeJavaFile( firstFile );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFilesTheFirstKnowsTheSecondWithPackages() throws Exception {
        createWSFolder( "org" );
        createWSFolder( "org/doublemill" );
        createWSFolder( "org/doublemill/model" );
        createWSFolder( "org/doublemill/model/ai" );
        createWSFolder( "org/doublemill/model/util" );
        IFile firstFile = createWSFile( "org/doublemill/model/ai/Acd_GameStateAI.java", loadContent("Acd_GameStateAI.test") );
        IFile secondFile = createWSFile( "org/doublemill/model/util/Acd_LRUCache.java", loadContent("Acd_LRUCache.test") );
        computeJavaFile( secondFile );
        computeJavaFile( firstFile );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
//        assertEquals( 1, model.getSumOfAllDirectChildrenOfAllClasses() );
//        assertEquals( 3, model.getSumOfAllKnownChildrenOfAllClasses() );
    }
    
    @Test
    public void twoFilesTheSecondKnowsTheFirstWithPackages() throws Exception {
        createWSFolder( "org" );
        createWSFolder( "org/doublemill" );
        createWSFolder( "org/doublemill/model" );
        createWSFolder( "org/doublemill/model/ai" );
        createWSFolder( "org/doublemill/model/util" );
        IFile firstFile = createWSFile( "org/doublemill/model/ai/Acd_GameStateAI.java", loadContent("Acd_GameStateAI.test") );
        IFile secondFile = createWSFile( "org/doublemill/model/util/Acd_LRUCache.java", loadContent("Acd_LRUCache.test") );
        computeJavaFile( firstFile );
        computeJavaFile( secondFile );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
//        assertEquals( 1, model.getSumOfAllDirectChildrenOfAllClasses() );
//        assertEquals( 3, model.getSumOfAllKnownChildrenOfAllClasses() );
    }
    
    @Test
    public void twoFiles_TheFirstKnowsTheSecond_SecondIsDeleted() throws Exception {
        IFile firstFile = createWSFile( "Acd11a.java", loadContent("Acd11a.test") );
        IFile secondFile = createAndCompute("11b");
        computeJavaFile( firstFile );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
        writeModel.dropRawData( secondFile );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItKnowsAnotherWhichIsMissing() throws Exception {
        createAndCompute( "11a" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void x_oneFile_ItKnowsAnotherWhichIsCreatedLater() throws Exception {
        createAndCompute( "11a" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
        createAndCompute("11b");
        // dieses Problem muss man durch geeignete Change-Sets loesen -> Leif fragen
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void oneFile_ItIsKnownByAnotherWhichIsCreatedLater() throws Exception {
        createAndCompute( "11b" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
        createAndCompute("11a");
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFiles_TheFirstKnowsTheSecond_FirstIsDeleted() throws Exception {
        IFile firstFile = createWSFile( "Acd11a.java", loadContent("Acd11a.test") );
        createAndCompute("11b");
        computeJavaFile( firstFile );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
        writeModel.dropRawData( firstFile );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    @Test
    public void twoFilesKnowEachOther() throws Exception {
        IFile file = createWSFile( "Acd10a.java", loadContent("Acd10a.test") );
        createAndCompute("10b");
        computeJavaFile( file );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
//        assertEquals( 2, model.getSumOfAllDirectChildrenOfAllClasses() );
//        assertEquals( 4, model.getSumOfAllKnownChildrenOfAllClasses() );
    }
    
    @Test
    public void twoFilesKnowEachOtherTheSecondIsDeleted() throws Exception {
        IFile firstFile = createWSFile( "Acd10a.java", loadContent("Acd10a.test") );
        IFile secondFile = createAndCompute("10b");
        computeJavaFile( firstFile );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
        writeModel.dropRawData( secondFile );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1.0, getACD(), 0.0001 );
    }
    
    private double getACD() {
        return new AcdSQIComputer().getRelativeACD(); // TODO!! aus model lesen
    }

    private IFile createAndCompute( String filenumber ) throws CoreException, Exception {
        return createAndCompute( filenumber, "Acd" );
    }
}
