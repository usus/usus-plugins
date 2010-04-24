package org.projectusus.core.filerelations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.ClassDescriptorUtil.createClassDescriptor;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isEmptySet;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isSetOf;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class FileRelationMetricsRemoveTest {

    private FileRelationMetrics metrics;
    private ClassDescriptor descriptor1;
    private ClassDescriptor descriptor2;
    private ClassDescriptor descriptor3;
    private IFile file1;
    private IFile file2;
    private IFile file3;

    @Before
    public void setupClassDescriptors() {
        metrics = new FileRelationMetrics();
        descriptor1 = createClassDescriptor( "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( "Descriptor2" ); //$NON-NLS-1$
        descriptor3 = createClassDescriptor( "Descriptor3" ); //$NON-NLS-1$
        file1 = mock( IFile.class );
        file2 = mock( IFile.class );
        file3 = mock( IFile.class );
    }

    @Test
    public void mocksAreNotEqual() {
        assertFalse( file1.equals( file2 ) );
    }

    @Test
    public void noRelations() {
        metrics.remove( file1 );
    }

    @Test
    public void oneRelationSameFile() {
        setupTwoWithOneFile();
        metrics.remove( file1 );
        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
    }

    @Test
    public void oneRelationDifferentFilesRemoveSource() {
        setupTwoWithTwoFiles();
        metrics.remove( file1 );
        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
    }

    @Test
    public void oneRelationDifferentFilesRemoveTarget() {
        setupTwoWithTwoFiles();
        metrics.remove( file2 );
        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
    }

    @Test
    public void twoRelationsOneFile() {
        setupThreeWithOneFile();

        metrics.remove( file1 );

        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor3 ), isEmptySet() );
    }

    @Test
    public void twoRelationsThreeFilesRemoveFirst() {
        setupThreeWithThreeFiles();

        metrics.remove( file1 );

        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor2 ), isSetOf( descriptor3 ) );
        assertThat( metrics.getChildren( descriptor3 ), isEmptySet() );
    }

    @Test
    public void twoRelationsThreeFilesRemoveSecond() {
        setupThreeWithThreeFiles();

        metrics.remove( file2 );

        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor3 ), isEmptySet() );
    }

    @Test
    public void twoRelationsThreeFilesRemoveThird() {
        setupThreeWithThreeFiles();

        metrics.remove( file3 );

        assertThat( metrics.getChildren( descriptor1 ), isSetOf( descriptor2 ) );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor3 ), isEmptySet() );
    }

    private void setupTwoWithOneFile() {
        when( descriptor1.getFile() ).thenReturn( file1 );
        when( descriptor2.getFile() ).thenReturn( file1 );
        metrics.addFileRelation( descriptor1, descriptor2 );
    }

    private void setupTwoWithTwoFiles() {
        when( descriptor1.getFile() ).thenReturn( file1 );
        when( descriptor2.getFile() ).thenReturn( file2 );
        metrics.addFileRelation( descriptor1, descriptor2 );
    }

    private void setupThreeWithOneFile() {
        when( descriptor1.getFile() ).thenReturn( file1 );
        when( descriptor2.getFile() ).thenReturn( file1 );
        when( descriptor3.getFile() ).thenReturn( file1 );
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
    }

    private void setupThreeWithThreeFiles() {
        when( descriptor1.getFile() ).thenReturn( file1 );
        when( descriptor2.getFile() ).thenReturn( file2 );
        when( descriptor3.getFile() ).thenReturn( file3 );
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
    }

}
