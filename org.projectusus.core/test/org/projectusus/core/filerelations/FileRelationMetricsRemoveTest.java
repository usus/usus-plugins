package org.projectusus.core.filerelations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isEmptySet;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isSetOf;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class FileRelationMetricsRemoveTest {

    private ClassDescriptor descriptor1;
    private ClassDescriptor descriptor2;
    private ClassDescriptor descriptor3;
    private IFile file1;
    private IFile file2;
    private IFile file3;

    @Before
    public void setupClassDescriptors() {
        file1 = mock( IFile.class );
        file2 = mock( IFile.class );
        file3 = mock( IFile.class );
    }

    public static ClassDescriptor createClassDescriptor( IFile file, String name ) {
        return ClassDescriptor.of( file, new Classname( name ), Packagename.of( name ) );
    }

    @Test
    public void mocksAreNotEqual() {
        assertFalse( file1.equals( file2 ) );
    }

    @Test
    public void noRelations() {
        setupOneWithOneFile();
        descriptor1.remove();
    }

    @Test
    public void oneRelationSameFile() {
        setupTwoWithOneFile();
        descriptor1.remove();
        descriptor2.remove();
        assertThat( descriptor1.getChildren(), isEmptySet() );
        assertThat( descriptor2.getChildren(), isEmptySet() );
    }

    @Test
    public void oneRelationDifferentFilesRemoveSource() {
        setupTwoWithTwoFiles();
        descriptor1.remove();
        assertThat( descriptor1.getChildren(), isEmptySet() );
        assertThat( descriptor2.getChildren(), isEmptySet() );
    }

    @Test
    public void oneRelationDifferentFilesRemoveTarget() {
        setupTwoWithTwoFiles();
        descriptor2.remove();
        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isEmptySet() );
        checkRelationsToRepair( descriptor1, descriptor2 );
    }

    @Test
    public void twoRelationsOneFile() {
        setupThreeWithOneFile();

        descriptor1.remove();
        descriptor2.remove();
        descriptor3.remove();

        assertThat( descriptor1.getChildren(), isEmptySet() );
        assertThat( descriptor2.getChildren(), isEmptySet() );
        assertThat( descriptor3.getChildren(), isEmptySet() );
    }

    @Test
    public void twoRelationsThreeFilesRemoveFirst() {
        setupThreeWithThreeFiles();

        descriptor1.remove();

        assertThat( descriptor1.getChildren(), isEmptySet() );
        assertThat( descriptor2.getChildren(), isSetOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), isEmptySet() );
    }

    @Test
    public void twoRelationsThreeFilesRemoveSecond() {
        setupThreeWithThreeFiles();

        descriptor2.remove();

        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isEmptySet() );
        assertThat( descriptor3.getChildren(), isEmptySet() );

        checkRelationsToRepair( descriptor1, descriptor2 );
    }

    @Test
    public void twoRelationsThreeFilesRemoveThird() {
        setupThreeWithThreeFiles();

        descriptor3.remove();

        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isSetOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), isEmptySet() );

        checkRelationsToRepair( descriptor2, descriptor3 );
    }

    private void setupOneWithOneFile() {
        descriptor1 = createClassDescriptor( file1, "Descriptor1" ); //$NON-NLS-1$
    }

    private void setupTwoWithOneFile() {
        descriptor1 = createClassDescriptor( file1, "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( file1, "Descriptor2" ); //$NON-NLS-1$
        FileRelation.of( descriptor1, descriptor2 );
    }

    private void setupTwoWithTwoFiles() {
        descriptor1 = createClassDescriptor( file1, "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( file2, "Descriptor2" ); //$NON-NLS-1$
        FileRelation.of( descriptor1, descriptor2 );
    }

    private void setupThreeWithOneFile() {
        descriptor1 = createClassDescriptor( file1, "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( file1, "Descriptor2" ); //$NON-NLS-1$
        descriptor3 = createClassDescriptor( file1, "Descriptor3" ); //$NON-NLS-1$
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
    }

    private void setupThreeWithThreeFiles() {
        descriptor1 = createClassDescriptor( file1, "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( file2, "Descriptor2" ); //$NON-NLS-1$
        descriptor3 = createClassDescriptor( file3, "Descriptor3" ); //$NON-NLS-1$
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
    }

    private void checkRelationsToRepair( ClassDescriptor source, ClassDescriptor target ) {
        List<FileRelation> relationsThatNeedRepair = DefectFileRelations.extractRelationsRegisteredForRepair();
        assertEquals( 1, relationsThatNeedRepair.size() );
        FileRelation relationToRepair = relationsThatNeedRepair.get( 0 );
        assertEquals( relationToRepair.getTargetDescriptor(), target );
        relationToRepair.remove();
        assertThat( source.getChildren(), isEmptySet() );
    }

}
