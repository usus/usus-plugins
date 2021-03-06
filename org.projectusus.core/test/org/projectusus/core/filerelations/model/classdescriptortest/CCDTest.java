package org.projectusus.core.filerelations.model.classdescriptortest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.UsusModelProvider;

public class CCDTest {

    private ClassDescriptor descriptor1;
    private ClassDescriptor descriptor2;
    private ClassDescriptor descriptor3;

    @Before
    public void setupClassDescriptors() {
        UsusModelProvider.clear();
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
        assertEquals( 1, descriptor1.getCCD() );
        assertEquals( 1, descriptor2.getCCD() );
        assertEquals( 1, descriptor3.getCCD() );
    }

    @Test
    public void oneRelation() {
        descriptor1.addChild( descriptor2 );
        assertEquals( 2, descriptor1.getCCD() );
        assertEquals( 1, descriptor2.getCCD() );
    }

    @Test
    public void oneClassKnows2() {
        descriptor1.addChild( descriptor2 );
        descriptor1.addChild( descriptor3 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 1, descriptor2.getCCD() );
        assertEquals( 1, descriptor3.getCCD() );
    }

    @Test
    public void oneClassKnows1Knows1() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 2, descriptor2.getCCD() );
        assertEquals( 1, descriptor3.getCCD() );
    }

    @Test
    public void twoClassCycle() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor1 );
        assertEquals( 2, descriptor1.getCCD() );
        assertEquals( 2, descriptor2.getCCD() );
    }

    @Test
    public void threeClassCycle() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor1 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 3, descriptor2.getCCD() );
        assertEquals( 3, descriptor3.getCCD() );
    }

    @Test
    public void threeClasses2InCycle() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor2 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 2, descriptor2.getCCD() );
        assertEquals( 2, descriptor3.getCCD() );
    }

    @Test
    public void transitiveChildrenWithSelfLoop() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 2, descriptor2.getCCD() );
        assertEquals( 1, descriptor3.getCCD() );
    }

    @Test
    public void calculateManyRelations() {
        descriptor1.addChild( descriptor2 );
        descriptor1.addChild( descriptor3 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor2 );
        descriptor3.addChild( descriptor1 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 3, descriptor2.getCCD() );
        assertEquals( 3, descriptor3.getCCD() );
    }

    @Test
    public void transitiveRelationsFromCyclicRelationsIncludingStart() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor2 );
        descriptor3.addChild( descriptor1 );
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 3, descriptor2.getCCD() );
        assertEquals( 3, descriptor3.getCCD() );
    }

    @Test
    public void secondQueryingOfCCDYieldsTheSameResult() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor2 );
        descriptor3.addChild( descriptor1 );
        descriptor1.getCCD();
        descriptor2.getCCD();
        descriptor3.getCCD();
        assertEquals( 3, descriptor1.getCCD() );
        assertEquals( 3, descriptor2.getCCD() );
        assertEquals( 3, descriptor3.getCCD() );
    }

}
