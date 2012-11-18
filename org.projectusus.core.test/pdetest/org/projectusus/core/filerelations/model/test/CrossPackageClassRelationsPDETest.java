package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.CrossPackageClassRelations;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class CrossPackageClassRelationsPDETest extends PDETestForMetricsComputation {

    @Test
    public void emptyProject() throws Exception {
        Set<ClassDescriptor> representers = new CrossPackageClassRelations().keySet();
        assertEquals( 0, representers.size() );
    }

    @Test
    public void oneClassInOneFile() throws Exception {
        createFileAndBuild( "_1" );
        Set<ClassDescriptor> representers = new CrossPackageClassRelations().keySet();
        assertEquals( 0, representers.size() );
    }

    private void checkName( ClassDescriptor node, String name1, String name2 ) {
        if( !node.getClassname().equals( name1 ) && !node.getClassname().equals( name2 ) ) {
            fail( "Falscher Name" );
        }
    }

    @Test
    public void twoClassesInOnePackageKnowEachOther() throws Exception {
        createFileAndBuild( "_file1Knows2" );
        createFileAndBuild( "_file2Knows1" );

        checkTwoNodesWithOneChildEach();

        Set<ClassDescriptor> representers = new CrossPackageClassRelations().keySet();
        assertEquals( 0, representers.size() );
    }

    @Test
    public void twoClassesInTwoPackagesKnowEachOther() throws Exception {
        project.createFolder( "package1" );
        project.createFolder( "package2" );
        project.createFile( "package1/MetricsAccessor_package1file1Knows2.java", loadResource( "MetricsAccessor_package1file1Knows2.test" ) );
        workspace.buildFullyAndWait();
        project.createFile( "package2/MetricsAccessor_package2file2Knows1.java", loadResource( "MetricsAccessor_package2file2Knows1.test" ) );
        workspace.buildFullyAndWait();

        checkTwoNodesWithOneChildEach();

        Set<ClassDescriptor> representers = new CrossPackageClassRelations().keySet();
        assertEquals( 2, representers.size() );
    }

    private void checkTwoNodesWithOneChildEach() {
        Set<ClassDescriptor> allRepresenters = ClassDescriptor.getAll();
        assertEquals( 2, allRepresenters.size() );
        ClassDescriptor node = allRepresenters.iterator().next();
        assertEquals( 1, node.getChildren().size() );
        node = allRepresenters.iterator().next();
        assertEquals( 1, node.getChildren().size() );
    }

    public void twoClassesInTwoFilesKnowEachOtherOneIsRemoved() throws Exception {
        createFileAndBuild( "_file1Knows2" );
        IFile file = createFileAndBuild( "_file2Knows1" );

        Set<ClassDescriptor> representers = new CrossPackageClassRelations().keySet();
        assertEquals( 2, representers.size() );
        ClassDescriptor node = representers.iterator().next();
        assertEquals( 1, node.getChildren().size() );
        node = representers.iterator().next();
        assertEquals( 1, node.getChildren().size() );

        project.updateContent( file, loadResource( "MetricsAccessor_file2Knows1Not.test" ) );
        workspace.buildFullyAndWait();

        representers = new CrossPackageClassRelations().keySet();
        assertEquals( 2, representers.size() );
        node = representers.iterator().next();
        checkName( node, "MetricsAccessor_file1Knows2", "MetricsAccessor_file2Knows1" );
        checkChildren( node, "MetricsAccessor_file1Knows2" );
        node = representers.iterator().next();
        checkName( node, "MetricsAccessor_file1Knows2", "MetricsAccessor_file2Knows1" );
        checkChildren( node, "MetricsAccessor_file1Knows2" );
    }

    private void checkChildren( ClassDescriptor node, String name ) {
        if( node.getClassname().equals( name ) ) {
            assertEquals( 1, node.getChildren().size() );
        } else {
            assertEquals( 0, node.getChildren().size() );
        }
    }

    protected IFile createFile( String filenumber ) throws Exception {
        return super.createFile( "MetricsAccessor" + filenumber );
    }

}
