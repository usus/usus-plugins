package org.projectusus.core.statistics.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class GetAllClassDescriptorsPDETest extends PDETestForMetricsComputation {

    @Test
    public void emptyProject() throws Exception {
        Set<ClassDescriptor> representers = ClassDescriptor.getAll();
        assertEquals( 0, representers.size() );
    }

    @Test
    public void oneClassInOneFile() throws Exception {
        createFileAndBuild( "_1" );
        Set<ClassDescriptor> representers = ClassDescriptor.getAll();
        assertEquals( 1, representers.size() );
        ClassDescriptor node = representers.iterator().next();
        assertEquals( 0, node.getChildren().size() );
    }

    @Test
    public void twoClassesInOneFileKnowEachOther() throws Exception {
        createFileAndBuild( "_twoKnowEachOther" );
        Set<ClassDescriptor> representers = ClassDescriptor.getAll();
        assertEquals( 2, representers.size() );
        ClassDescriptor node = representers.iterator().next();
        checkName( node, "Acd2", "Acd2Helper" );
        assertEquals( 1, node.getChildren().size() );
        node = representers.iterator().next();
        checkName( node, "Acd2", "Acd2Helper" );
        assertEquals( 1, node.getChildren().size() );
    }

    private void checkName( ClassDescriptor node, String name1, String name2 ) {
        if( !node.getClassname().toString().equals( name1 ) && !node.getClassname().toString().equals( name2 ) ) {
            fail( "Falscher Name: " + node.getClassname() );
        }
    }

    @Test
    public void twoClassesInOneFileKnowEachOtherOneIsRemoved() throws Exception {
        IFile file = createFileAndBuild( "_twoKnowEachOther" );
        project.updateContent( file, loadResource( "MetricsAccessor_oneKnowsTheOther.test" ) );
        workspace.buildFullyAndWait();
        Set<ClassDescriptor> representers = ClassDescriptor.getAll();
        assertEquals( 2, representers.size() );
        ClassDescriptor node = representers.iterator().next();
        checkName( node, "Acd2", "Acd2Helper" );
        checkChildren( node, "Acd2" );
        node = representers.iterator().next();
        checkName( node, "Acd2", "Acd2Helper" );
        checkChildren( node, "Acd2" );
    }

    @Test
    public void twoClassesInTwoFilesKnowEachOtherOneIsRemoved() throws Exception {
        createFileAndBuild( "_file1Knows2" );
        IFile file = createFileAndBuild( "_file2Knows1" );

        Set<ClassDescriptor> representers = ClassDescriptor.getAll();
        assertEquals( 2, representers.size() );
        ClassDescriptor node = representers.iterator().next();
        assertEquals( 1, node.getChildren().size() );
        node = representers.iterator().next();
        assertEquals( 1, node.getChildren().size() );

        project.updateContent( file, loadResource( "MetricsAccessor_file2Knows1Not.test" ) );
        workspace.buildFullyAndWait();

        representers = ClassDescriptor.getAll();
        assertEquals( 2, representers.size() );
        node = representers.iterator().next();
        checkName( node, "MetricsAccessor_file1Knows2", "MetricsAccessor_file2Knows1" );
        checkChildren( node, "MetricsAccessor_file1Knows2" );
        node = representers.iterator().next();
        checkName( node, "MetricsAccessor_file1Knows2", "MetricsAccessor_file2Knows1" );
        checkChildren( node, "MetricsAccessor_file1Knows2" );
    }

    private void checkChildren( ClassDescriptor node, String name ) {
        if( node.getClassname().toString().equals( name ) ) {
            assertEquals( 1, node.getChildren().size() );
        } else {
            assertEquals( 0, node.getChildren().size() );
        }
    }

    protected IFile createFile( String filenumber ) throws Exception {
        return super.createFile( "MetricsAccessor" + filenumber );
    }

}
