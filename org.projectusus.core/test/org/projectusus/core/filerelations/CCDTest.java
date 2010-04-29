package org.projectusus.core.filerelations;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.filerelations.ClassDescriptorUtil.createClassDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class CCDTest {

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
        assertEquals( 1, metrics.getCCD( descriptor1 ) );
    }

    @Test
    public void oneRelation() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        assertEquals( 2, metrics.getCCD( descriptor1 ) );
        assertEquals( 1, metrics.getCCD( descriptor2 ) );
    }

    @Test
    public void oneClassKnows2() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor1, descriptor3 );
        assertEquals( 3, metrics.getCCD( descriptor1 ) );
        assertEquals( 1, metrics.getCCD( descriptor2 ) );
        assertEquals( 1, metrics.getCCD( descriptor3 ) );
    }

    @Test
    public void oneClassKnows1Knows1() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
        assertEquals( 3, metrics.getCCD( descriptor1 ) );
        assertEquals( 2, metrics.getCCD( descriptor2 ) );
        assertEquals( 1, metrics.getCCD( descriptor3 ) );
    }

    @Test
    public void threeClassCycle() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
        metrics.addFileRelation( descriptor3, descriptor1 );
        assertEquals( 3, metrics.getCCD( descriptor1 ) );
        assertEquals( 3, metrics.getCCD( descriptor2 ) );
        assertEquals( 3, metrics.getCCD( descriptor3 ) );
    }

    @Test
    public void threeClasses2InCycle() {
        metrics.addFileRelation( descriptor1, descriptor2 );
        metrics.addFileRelation( descriptor2, descriptor3 );
        metrics.addFileRelation( descriptor3, descriptor2 );
        assertEquals( 3, metrics.getCCD( descriptor1 ) );
        assertEquals( 2, metrics.getCCD( descriptor2 ) );
        assertEquals( 2, metrics.getCCD( descriptor3 ) );
    }
}
