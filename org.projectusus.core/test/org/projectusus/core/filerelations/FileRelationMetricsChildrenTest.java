package org.projectusus.core.filerelations;

import static org.junit.Assert.assertThat;
import static org.projectusus.core.filerelations.ClassDescriptorUtil.createClassDescriptor;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isEmptySet;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isSetOf;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class FileRelationMetricsChildrenTest {

    private FileRelationMetrics metrics = new FileRelationMetrics();
    private ClassDescriptor descriptor1;
    private ClassDescriptor descriptor2;
    private ClassDescriptor descriptor3;

    @Before
    public void setupClassDescriptors() {
        descriptor1 = createClassDescriptor( "Descriptor1" ); //$NON-NLS-1$
        descriptor2 = createClassDescriptor( "Descriptor2" ); //$NON-NLS-1$
        descriptor3 = createClassDescriptor( "Descriptor3" ); //$NON-NLS-1$
    }

    @Test
    public void noRelations() {
        assertThat( metrics.getChildren( descriptor1 ), isEmptySet() );
    }

    @Test
    public void oneRelation() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        assertThat( metrics.getChildren( descriptor1 ), isSetOf( descriptor2 ) );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
    }

    @Test
    public void oneClassKnows2() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor1, descriptor3 );
        assertThat( metrics.getChildren( descriptor1 ), isSetOf( descriptor2, descriptor3 ) );
        assertThat( metrics.getChildren( descriptor2 ), isEmptySet() );
        assertThat( metrics.getChildren( descriptor3 ), isEmptySet() );
    }

    @Test
    public void oneClassKnows1Knows1() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
        assertThat( metrics.getChildren( descriptor1 ), isSetOf( descriptor2 ) );
        assertThat( metrics.getChildren( descriptor2 ), isSetOf( descriptor3 ) );
        assertThat( metrics.getChildren( descriptor3 ), isEmptySet() );
    }

    @Test
    public void threeClassCycle() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
        metrics.addFileRelation( descriptor3, descriptor1 );
        assertThat( metrics.getChildren( descriptor1 ), isSetOf( descriptor2 ) );
        assertThat( metrics.getChildren( descriptor2 ), isSetOf( descriptor3 ) );
        assertThat( metrics.getChildren( descriptor3 ), isSetOf( descriptor1 ) );
    }

    @Test
    public void threeClasses2InCycle() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
        metrics.addFileRelation( descriptor3, descriptor2 );
        assertThat( metrics.getChildren( descriptor1 ), isSetOf( descriptor2 ) );
        assertThat( metrics.getChildren( descriptor2 ), isSetOf( descriptor3 ) );
        assertThat( metrics.getChildren( descriptor3 ), isSetOf( descriptor2 ) );
    }
}
