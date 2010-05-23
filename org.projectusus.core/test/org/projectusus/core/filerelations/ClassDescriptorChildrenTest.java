package org.projectusus.core.filerelations;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isEmptySet;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isSetOf;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class ClassDescriptorChildrenTest {

    private ClassDescriptor descriptor1;
    private ClassDescriptor descriptor2;
    private ClassDescriptor descriptor3;

    @Before
    public void setupClassDescriptors() {
        descriptor1 = createClassDescriptor( "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( "Descriptor2" ); //$NON-NLS-1$
        descriptor3 = createClassDescriptor( "Descriptor3" ); //$NON-NLS-1$
    }

    public static ClassDescriptor createClassDescriptor( String name ) {
        IFile file = mock( IFile.class );
        return ClassDescriptor.of( file, new Classname( name ), Packagename.of( name ) );
    }

    @Test
    public void noRelations() {
        assertThat( descriptor1.getChildren(), isEmptySet() );
    }

    @Test
    public void oneRelation() {
        FileRelation.of( descriptor1, descriptor2 );
        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isEmptySet() );
    }

    @Test
    public void oneClassKnows2() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor1, descriptor3 );
        assertThat( descriptor1.getChildren(), isSetOf( descriptor2, descriptor3 ) );
        assertThat( descriptor2.getChildren(), isEmptySet() );
        assertThat( descriptor3.getChildren(), isEmptySet() );
    }

    @Test
    public void oneClassKnows1Knows1() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isSetOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), isEmptySet() );
    }

    @Test
    public void threeClassCycle() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        FileRelation.of( descriptor3, descriptor1 );
        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isSetOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), isSetOf( descriptor1 ) );
    }

    @Test
    public void threeClasses2InCycle() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        FileRelation.of( descriptor3, descriptor2 );
        assertThat( descriptor1.getChildren(), isSetOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), isSetOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), isSetOf( descriptor2 ) );
    }
}
