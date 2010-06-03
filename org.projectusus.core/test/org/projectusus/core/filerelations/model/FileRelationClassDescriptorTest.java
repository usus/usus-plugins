package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.internal.metrics.ACDCalculator;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class FileRelationClassDescriptorTest {

    private IFile file = mock( IFile.class );
    private Packagename packagename = Packagename.of( "packagename" ); //$NON-NLS-1$
    private String class1 = "Name1"; //$NON-NLS-1$
    private Classname classname1 = new Classname( class1 );
    private String class2 = "Name2"; //$NON-NLS-1$
    private Classname classname2 = new Classname( class2 );
    private String class3 = "Name3"; //$NON-NLS-1$
    private Classname classname3 = new Classname( class3 );

    @Before
    public void init() {
        UsusModel.clear();
    }

    @Test
    public void oneClassDescriptorNoRelations() {
        ClassDescriptor.of( file, classname1, packagename );
        checkOne();
    }

    @Test
    public void twoClassDescriptorsOneRelation() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor target = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( target );
        checkTwo();
    }

    @Test
    public void threeClassDescriptorsTwoRelations() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor middle = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( middle );
        ClassDescriptor target = ClassDescriptor.of( file, classname3, packagename );
        middle.addChild( target );
        checkThree();
    }

    @Test
    public void threeClassDescriptorsTwoRelationsFirstAndSecondRemovedInBetween() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor middle = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( middle );
        checkTwo();

        source.prepareRemoval();
        cleanupDescriptors( 1 );
        checkOne();

        middle.prepareRemoval();
        cleanupDescriptors( 1 );
        checkNull();

        ClassDescriptor source2 = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor middle2 = ClassDescriptor.of( file, classname2, packagename );
        source2.addChild( middle2 );
        checkTwo();

        ClassDescriptor target = ClassDescriptor.of( file, classname3, packagename );
        middle2.addChild( target );
        checkThree();
    }

    @Test
    public void threeClassDescriptorsTwoRelationsSecondAndFirstRemovedInBetween() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor middle = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( middle );
        checkTwo();

        middle.prepareRemoval();
        cleanupDescriptors( 1 );
        checkOne();

        source.prepareRemoval();
        cleanupDescriptors( 1 );
        checkNull();

        ClassDescriptor source2 = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor middle2 = ClassDescriptor.of( file, classname2, packagename );
        source2.addChild( middle2 );
        checkTwo();

        ClassDescriptor target = ClassDescriptor.of( file, classname3, packagename );
        middle2.addChild( target );
        checkThree();
    }

    @Test
    public void threeClassDescriptorsTwoRelationsSecondRemovedInBetween() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor middle = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( middle );
        checkTwo();

        middle.prepareRemoval();
        cleanupDescriptors( 1 );
        checkOne();

        ClassDescriptor middle2 = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( middle2 );
        checkTwo();

        ClassDescriptor target = ClassDescriptor.of( file, classname3, packagename );
        middle2.addChild( target );
        checkThree();
    }

    @Test
    public void twoClassDescriptorsOneRelationRemoved() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor target = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( target );
        checkTwo();

        target.prepareRemoval();
        cleanupDescriptors( 1 );
        checkOne();
    }

    @Test
    public void twoClassDescriptorsOneRelationBothRemovedSourceFirst() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor target = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( target );
        checkTwo();

        source.prepareRemoval();
        target.prepareRemoval();
        cleanupDescriptors( 2 );
        checkNull();
    }

    @Test
    public void twoClassDescriptorsOneRelationBothRemovedTargetFirst() {
        ClassDescriptor source = ClassDescriptor.of( file, classname1, packagename );
        ClassDescriptor target = ClassDescriptor.of( file, classname2, packagename );
        source.addChild( target );
        checkTwo();

        target.prepareRemoval();
        source.prepareRemoval();
        cleanupDescriptors( 2 );
        checkNull();
    }

    private void checkNull() {
        assertEquals( 0, ClassDescriptor.getAll().size() );
        assertEquals( 0.0, ACDCalculator.getRelativeACD(), 0.0001 );
    }

    private void checkOne() {
        Set<ClassDescriptor> descriptors = ClassDescriptor.getAll();
        assertEquals( 1, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            assertEquals( 1, descriptor.getCCD() );
            assertEquals( 1, descriptor.getTransitiveParentCount() );
        }
        assertEquals( 1.0, ACDCalculator.getRelativeACD(), 0.0001 );
    }

    private void checkTwo() {
        Set<ClassDescriptor> descriptors = ClassDescriptor.getAll();
        assertEquals( 2, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            if( descriptor.getClassname().toString().equals( class1 ) ) {
                assertEquals( 2, descriptor.getCCD() );
                assertEquals( 1, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( class2 ) ) {
                assertEquals( 1, descriptor.getCCD() );
                assertEquals( 2, descriptor.getTransitiveParentCount() );
            } else {
                fail( "Found unknown ClassDescriptor" ); //$NON-NLS-1$
            }
        }
        assertEquals( 3 / 4.0, ACDCalculator.getRelativeACD(), 0.0001 );
    }

    private void checkThree() {
        Set<ClassDescriptor> descriptors = ClassDescriptor.getAll();
        assertEquals( 3, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            if( descriptor.getClassname().toString().equals( class1 ) ) {
                assertEquals( 3, descriptor.getCCD() );
                assertEquals( 1, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( class2 ) ) {
                assertEquals( 2, descriptor.getCCD() );
                assertEquals( 2, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( class3 ) ) {
                assertEquals( 1, descriptor.getCCD() );
                assertEquals( 3, descriptor.getTransitiveParentCount() );
            } else {
                fail( "Found unknown ClassDescriptor" ); //$NON-NLS-1$
            }
        }
        assertEquals( 6 / 9.0, ACDCalculator.getRelativeACD(), 0.0001 );
    }

    private void cleanupDescriptors( int count ) {
        Set<ClassDescriptor> descriptorsForCleanup = ClassDescriptorCleanup.extractDescriptorsRegisteredForCleanup();
        assertEquals( count, descriptorsForCleanup.size() );
        for( ClassDescriptor descriptor : descriptorsForCleanup ) {
            descriptor.removeFromPool();
        }
    }

}
