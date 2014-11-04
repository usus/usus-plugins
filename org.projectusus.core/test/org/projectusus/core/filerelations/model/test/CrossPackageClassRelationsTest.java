package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.CrossPackageClassRelations;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.UsusModelProvider;

public class CrossPackageClassRelationsTest {

    @Before
    public void setup() {
        UsusModelProvider.clear();
    }

    @Test
    public void emptyProject_NoCrossPackageRelations() {
        assertEquals( 0, new CrossPackageClassRelations().getAllDirectRelations().size() );
    }

    @Test
    public void oneClassInOneFile_NoCrossPackageRelations() {
        createClassDescriptor( "Descriptor1" );

        assertEquals( 0, new CrossPackageClassRelations().getAllDirectRelations().size() );
    }

    @Test
    public void twoClassesInOnePackageKnowEachOther_NoCrossPackageRelations() {
        ClassDescriptor descriptor1 = createClassDescriptorWithSamePackage( "Descriptor2" );
        ClassDescriptor descriptor2 = createClassDescriptorWithSamePackage( "Descriptor3" );
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor1 );

        checkTwoNodesWithOneChildEach( ClassDescriptor.getAll() );
        assertEquals( 0, new CrossPackageClassRelations().getAllDirectRelations().size() );
    }

    @Test
    public void twoClassesInTwoPackagesOneKnowsTheOther_OneCrossPackageRelation() {
        ClassDescriptor descriptor1 = createClassDescriptor( "Descriptor4" );
        ClassDescriptor descriptor2 = createClassDescriptor( "Descriptor5" );
        descriptor1.addChild( descriptor2 );

        checkTwoNodesWithOneChildEach( ClassDescriptor.getAll() );
        assertEquals( 1, new CrossPackageClassRelations().getAllDirectRelations().size() );
    }

    @Test
    public void twoClassesInTwoPackagesKnowEachOther_TwoCrossPackageRelations() {
        ClassDescriptor descriptor1 = createClassDescriptor( "Descriptor6" );
        ClassDescriptor descriptor2 = createClassDescriptor( "Descriptor7" );
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor1 );

        checkTwoNodesWithOneChildEach( ClassDescriptor.getAll() );
        assertEquals( 2, new CrossPackageClassRelations().getAllDirectRelations().size() );
    }

    private static ClassDescriptor createClassDescriptor( String name ) {
        IFile file = mock( IFile.class );
        return ClassDescriptor.of( file, new Classname( name, null ), Packagename.of( name, null ) );
    }

    private static ClassDescriptor createClassDescriptorWithSamePackage( String name ) {
        IFile file = mock( IFile.class );
        return ClassDescriptor.of( file, new Classname( name, null ), Packagename.of( "packagename", null ) );
    }

    private void checkTwoNodesWithOneChildEach( Set<ClassDescriptor> descriptors ) {
        assertEquals( 2, descriptors.size() );
        ClassDescriptor node = descriptors.iterator().next();
        assertEquals( 1, node.getChildren().size() );
        node = descriptors.iterator().next();
        assertEquals( 1, node.getChildren().size() );
    }
}
