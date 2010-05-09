package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.adapter.ForcedRecompute;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class ACDCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void singleClass() throws Exception {
        createFileAndBuild( "1" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoUnrelatedClasses() throws Exception {
        createFileAndBuild( "2" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.5, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses1knows2() throws Exception {
        createFileAndBuild( "3_1" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClassesKnowEachOther() throws Exception {
        createFileAndBuild( "_twoKnowEachOther" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        // TODO NR
        // assertEquals( 2, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 4, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoRelatedClasses1knows2Statically() throws Exception {
        createFileAndBuild( "_1knows2static" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses1knows2Generic() throws Exception {
        createFileAndBuild( "_1knows2generic" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses1knows2InGenericArgument() throws Exception {
        createFileAndBuild( "_1knows2InGenericArg" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses2know2() throws Exception {
        createFileAndBuild( "3_2" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void threeUnrelatedClasses() throws Exception {
        createFileAndBuild( "4" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 3, model.getAllClassRepresenters().size() );
        assertEquals( 0.3333, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses1knows2() throws Exception {
        createFileAndBuild( "5" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 3, model.getAllClassRepresenters().size() );
        assertEquals( 4 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses2know2() throws Exception {
        createFileAndBuild( "6" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 3, model.getAllClassRepresenters().size() );
        assertEquals( 5 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses3know2() throws Exception {
        createFileAndBuild( "7" );
        assertEquals( 3, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 3, model.getAllClassRepresenters().size() );
        assertEquals( 7 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void tenRelatedClasses1knows2() throws Exception {
        createFileAndBuild( "8" );
        assertEquals( 10, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 10, model.getAllClassRepresenters().size() );
        assertEquals( 0.11, getACD(), 0.0001 );
    }

    @Test
    public void tenRelatedClasses1knows2know2() throws Exception {
        createFileAndBuild( "9" );
        assertEquals( 10, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 10, model.getAllClassRepresenters().size() );
        assertEquals( 0.2, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_11aKnows11b() throws Exception {
        createFile( "11b" );
        createFileAndBuild( "11a" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_11aLoaded2nd_Knows11bLoaded1st() throws Exception {
        createFileAndBuild( "11b" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createFile( "11a" );
        buildIncrementallyAndWait();
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_GameStateLoaded2nd_KnowsLRUCacheLoaded1st_WithPackages() throws Exception {
        createWSFolders();
        createWSFile( "org/doublemill/model/util/Acd_LRUCache.java", loadContent( "Acd_LRUCache.test" ) );
        buildFullyAndWait();
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createWSFile( "org/doublemill/model/ai/Acd_GameStateAI.java", loadContent( "Acd_GameStateAI.test" ) );
        buildIncrementallyAndWait();
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        // assertEquals( 1, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 3, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoFiles_GameStateLoaded1st_KnowsLRUCacheLoaded2nd_WithPackages() throws Exception {
        createWSFolders();
        createWSFile( "org/doublemill/model/ai/Acd_GameStateAI.java", loadContent( "Acd_GameStateAI.test" ) );
        buildFullyAndWait();
        System.out.println( "=====================" );
        System.out.println( "Erster Autobuild beendet." );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createWSFile( "org/doublemill/model/util/Acd_LRUCache.java", loadContent( "Acd_LRUCache.test" ) );
        buildIncrementallyAndWait();
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );
        System.out.println( "=====================" );
        System.out.println( "Zweiter Autobuild beendet." );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        System.out.println( "=====================" );
        System.out.println( "Test beendet." );
        // assertEquals( 1, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 3, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoFiles_11aLoaded2nd_Knows11bLoaded1st_11bIsDeleted() throws Exception {
        IFile file11b = createFileAndBuild( "11b" );
        createFileAndBuild( "11a" );
        buildFullyAndWait(); // seems to fix a racing condition that only occurs during testing
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        deleteFile( file11b );
        buildIncrementallyAndWait();
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItKnowsAnotherWhichIsMissing() throws Exception {
        createFileAndBuild( "11a" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItKnowsAnotherWhichIsCreatedLater() throws Exception {
        createFileAndBuild( "11a" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createFile( "11b" );
        buildIncrementallyAndWait();
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItIsKnownByAnotherWhichIsCreatedLater() throws Exception {
        createFileAndBuild( "11b" );
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createFile( "11a" );
        buildIncrementallyAndWait();
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_TheFirstKnowsTheSecond_FirstIsDeleted() throws Exception {
        createFileAndBuild( "11b" );
        IFile firstFile = createFileAndBuild( "11a" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        deleteFile( firstFile );
        buildIncrementallyAndWait();
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoFilesKnowEachOther() throws Exception {
        createFileAndBuild( "10a" );
        createFileAndBuild( "10b" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        // assertEquals( 2, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 4, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoFilesKnowEachOtherTheSecondIsDeleted() throws Exception {
        createFileAndBuild( "10a" );
        IFile secondFile = createFileAndBuild( "10b" );
        assertEquals( 2, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 2, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        deleteFile( secondFile );
        buildIncrementallyAndWait();
        assertEquals( 1, model.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, model.getAllClassRepresenters().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    // twoFilesOneClassIsRemovedFromFile

    private double getACD() {
        return UsusCorePlugin.getUsusModel().getRelativeACD();
    }

    protected IFile createFile( String filenumber ) throws Exception {
        return super.createFile( "Acd" + filenumber );
    }

    private void deleteFile( IFile file11b ) throws CoreException {
        deleteWSFile( file11b );
    }

    private void createWSFolders() throws CoreException {
        createWSFolder( "org" );
        createWSFolder( "org/doublemill" );
        createWSFolder( "org/doublemill/model" );
        createWSFolder( "org/doublemill/model/ai" );
        createWSFolder( "org/doublemill/model/util" );
    }
}
