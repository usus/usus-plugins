package org.projectusus.core.filerelations.model.classdescriptortest;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.projectusus.matchers.UsusMatchers.emptySet;
import static org.projectusus.matchers.UsusMatchers.setOf;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
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
        return ClassDescriptor.of( file, new Classname( name ), Packagename.of( name, null ) );
    }

    @Test
    public void noRelations() {
        assertThat( descriptor1.getChildren(), emptySet() );
    }

    @Test
    public void oneRelation() {
        descriptor1.addChild( descriptor2 );
        assertThat( descriptor1.getChildren(), setOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), emptySet() );
    }

    @Test
    public void oneClassKnows2() {
        descriptor1.addChild( descriptor2 );
        descriptor1.addChild( descriptor3 );
        assertThat( descriptor1.getChildren(), setOf( descriptor2, descriptor3 ) );
        assertThat( descriptor2.getChildren(), emptySet() );
        assertThat( descriptor3.getChildren(), emptySet() );
    }

    @Test
    public void oneClassKnows1Knows1() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        assertThat( descriptor1.getChildren(), setOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), setOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), emptySet() );
    }

    @Test
    public void threeClassCycle() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor1 );
        assertThat( descriptor1.getChildren(), setOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), setOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), setOf( descriptor1 ) );
    }

    @Test
    public void threeClasses2InCycle() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor2 );
        assertThat( descriptor1.getChildren(), setOf( descriptor2 ) );
        assertThat( descriptor2.getChildren(), setOf( descriptor3 ) );
        assertThat( descriptor3.getChildren(), setOf( descriptor2 ) );
    }
}
