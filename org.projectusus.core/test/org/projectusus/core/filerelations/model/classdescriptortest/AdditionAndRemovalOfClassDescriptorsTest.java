package org.projectusus.core.filerelations.model.classdescriptortest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.ClassDescriptorCleanup;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.UsusModelProvider;

public class AdditionAndRemovalOfClassDescriptorsTest {

    private IFile file = mock( IFile.class );
    private Packagename packagename = Packagename.of( "packagename", null ); //$NON-NLS-1$
    private Classname classname1 = new Classname( "Name1" );
    private Classname classname2 = new Classname( "Name2" );
    private Classname classname3 = new Classname( "Name3" );

    @Before
    public void init() {
        UsusModelProvider.clear();
    }

    @Test
    public void oneClassDescriptorNoRelations() {
        classDescriptorOf( classname1 );
        assertEquals( 1, ClassDescriptor.getAll().size() );
    }

    @Test
    public void twoClassDescriptorsOneRelation() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor target = classDescriptorOf( classname2 );
        source.addChild( target );
        assertEquals( 2, ClassDescriptor.getAll().size() );
    }

    @Test
    public void threeClassDescriptorsTwoRelations() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor middle = classDescriptorOf( classname2 );
        source.addChild( middle );
        ClassDescriptor target = classDescriptorOf( classname3 );
        middle.addChild( target );
        assertEquals( 3, ClassDescriptor.getAll().size() );
    }

    @Test
    public void threeClassDescriptorsTwoRelationsFirstAndSecondRemovedInBetween() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor middle = classDescriptorOf( classname2 );
        source.addChild( middle );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        source.prepareRemoval();
        cleanupDescriptors( 1 );
        assertEquals( 1, ClassDescriptor.getAll().size() );

        middle.prepareRemoval();
        cleanupDescriptors( 1 );
        assertEquals( 0, ClassDescriptor.getAll().size() );

        ClassDescriptor source2 = classDescriptorOf( classname1 );
        ClassDescriptor middle2 = classDescriptorOf( classname2 );
        source2.addChild( middle2 );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        ClassDescriptor target = classDescriptorOf( classname3 );
        middle2.addChild( target );
        assertEquals( 3, ClassDescriptor.getAll().size() );
    }

    @Test
    public void threeClassDescriptorsTwoRelationsSecondAndFirstRemovedInBetween() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor middle = classDescriptorOf( classname2 );
        source.addChild( middle );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        middle.prepareRemoval();
        cleanupDescriptors( 1 );
        assertEquals( 1, ClassDescriptor.getAll().size() );

        source.prepareRemoval();
        cleanupDescriptors( 1 );
        assertEquals( 0, ClassDescriptor.getAll().size() );

        ClassDescriptor source2 = classDescriptorOf( classname1 );
        ClassDescriptor middle2 = classDescriptorOf( classname2 );
        source2.addChild( middle2 );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        ClassDescriptor target = classDescriptorOf( classname3 );
        middle2.addChild( target );
        assertEquals( 3, ClassDescriptor.getAll().size() );
    }

    @Test
    public void threeClassDescriptorsTwoRelationsSecondRemovedInBetween() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor middle = classDescriptorOf( classname2 );
        source.addChild( middle );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        middle.prepareRemoval();
        cleanupDescriptors( 1 );
        assertEquals( 1, ClassDescriptor.getAll().size() );

        ClassDescriptor middle2 = classDescriptorOf( classname2 );
        source.addChild( middle2 );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        ClassDescriptor target = classDescriptorOf( classname3 );
        middle2.addChild( target );
        assertEquals( 3, ClassDescriptor.getAll().size() );
    }

    @Test
    public void twoClassDescriptorsOneRelationRemoved() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor target = classDescriptorOf( classname2 );
        source.addChild( target );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        target.prepareRemoval();
        cleanupDescriptors( 1 );
        assertEquals( 1, ClassDescriptor.getAll().size() );
    }

    @Test
    public void twoClassDescriptorsOneRelationBothRemovedSourceFirst() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor target = classDescriptorOf( classname2 );
        source.addChild( target );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        source.prepareRemoval();
        target.prepareRemoval();
        cleanupDescriptors( 2 );
        assertEquals( 0, ClassDescriptor.getAll().size() );
    }

    @Test
    public void twoClassDescriptorsOneRelationBothRemovedTargetFirst() {
        ClassDescriptor source = classDescriptorOf( classname1 );
        ClassDescriptor target = classDescriptorOf( classname2 );
        source.addChild( target );
        assertEquals( 2, ClassDescriptor.getAll().size() );

        target.prepareRemoval();
        source.prepareRemoval();
        cleanupDescriptors( 2 );
        assertEquals( 0, ClassDescriptor.getAll().size() );
    }

    private ClassDescriptor classDescriptorOf( Classname classname22 ) {
        return ClassDescriptor.of( file, classname22, packagename );
    }

    private void cleanupDescriptors( int count ) {
        Set<ClassDescriptor> descriptorsForCleanup = ClassDescriptorCleanup.extractDescriptorsRegisteredForCleanup();
        assertEquals( count, descriptorsForCleanup.size() );
        for( ClassDescriptor descriptor : descriptorsForCleanup ) {
            descriptor.removeFromPool();
        }
    }

}
