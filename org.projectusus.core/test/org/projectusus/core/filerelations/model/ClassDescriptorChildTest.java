package org.projectusus.core.filerelations.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.junit.Test;

@SuppressWarnings( "nls" )
public class ClassDescriptorChildTest {

    private IFile file = TestServiceManager.createFileMock();
    private String classNameString = "name";
    private Classname classname1 = new Classname( classNameString + "1" );
    private Classname classname2 = new Classname( classNameString + "2" );
    private Classname classname3 = new Classname( classNameString + "3" );
    private Packagename packagename = Packagename.of( "packagename" );
    private ClassDescriptor descriptor1 = ClassDescriptor.of( file, classname1, packagename );
    private ClassDescriptor descriptor2 = ClassDescriptor.of( file, classname2, packagename );
    private ClassDescriptor descriptor3 = ClassDescriptor.of( file, classname3, packagename );

    @Test
    public void noDirectChildren() {
        assertEquals( 0, descriptor1.getChildren().size() );
    }

    @Test
    public void oneDirectChild() {
        FileRelation.of( descriptor1, descriptor2 );
        assertEquals( 1, descriptor1.getChildren().size() );
        assertTrue( descriptor1.getChildren().contains( descriptor2 ) );
    }

    @Test
    public void oneDirectAndOneIndirectChild() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        assertEquals( 1, descriptor1.getChildren().size() );
        assertTrue( descriptor1.getChildren().contains( descriptor2 ) );
    }

    @Test
    public void oneDirectChildAndOneLoop() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        FileRelation.of( descriptor3, descriptor1 );
        assertEquals( 1, descriptor1.getChildren().size() );
        assertTrue( descriptor1.getChildren().contains( descriptor2 ) );
    }

    @Test
    public void noTransitiveChildren() {
        assertEquals( 1, descriptor1.getTransitiveChildren().size() );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor1 ) );
    }

    @Test
    public void oneTransitiveChild() {
        FileRelation.of( descriptor1, descriptor2 );
        assertEquals( 2, descriptor1.getTransitiveChildren().size() );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor1 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor2 ) );
    }

    @Test
    public void twoTransitiveChildren() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        assertEquals( 3, descriptor1.getTransitiveChildren().size() );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor1 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor2 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor3 ) );
    }

    @Test
    public void threeTransitiveChildrenWithLoop() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        FileRelation.of( descriptor3, descriptor1 );
        assertEquals( 3, descriptor1.getTransitiveChildren().size() );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor1 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor2 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor3 ) );
    }

    @Test
    public void transitiveChildrenWithSelfLoop() {
        FileRelation.of( descriptor1, descriptor2 );
        FileRelation.of( descriptor2, descriptor2 );
        FileRelation.of( descriptor2, descriptor3 );
        assertEquals( 3, descriptor1.getTransitiveChildren().size() );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor1 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor2 ) );
        assertTrue( descriptor1.getTransitiveChildren().contains( descriptor3 ) );
    }

}
