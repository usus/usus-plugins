package org.projectusus.core.filerelations.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;

@SuppressWarnings( "nls" )
public class ClassDescriptorDirectChildrenTest {

    private IFile file = TestServiceManager.createFileMock();
    private String classNameString = "name";
    private Classname classname1 = new Classname( classNameString + "1" );
    private Classname classname2 = new Classname( classNameString + "2" );
    private Classname classname3 = new Classname( classNameString + "3" );
    private Packagename packagename = Packagename.of( "packagename", null );
    private ClassDescriptor descriptor1 = ClassDescriptor.of( file, classname1, packagename );
    private ClassDescriptor descriptor2 = ClassDescriptor.of( file, classname2, packagename );
    private ClassDescriptor descriptor3 = ClassDescriptor.of( file, classname3, packagename );

    @Test
    public void noDirectChildren() {
        assertEquals( 0, descriptor1.getChildren().size() );
    }

    @Test
    public void oneDirectChild() {
        descriptor1.addChild( descriptor2 );
        assertEquals( 1, descriptor1.getChildren().size() );
        assertTrue( descriptor1.getChildren().contains( descriptor2 ) );
    }

    @Test
    public void oneDirectAndOneIndirectChild() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        assertEquals( 1, descriptor1.getChildren().size() );
        assertTrue( descriptor1.getChildren().contains( descriptor2 ) );
    }

    @Test
    public void oneDirectChildAndOneLoop() {
        descriptor1.addChild( descriptor2 );
        descriptor2.addChild( descriptor3 );
        descriptor3.addChild( descriptor1 );
        assertEquals( 1, descriptor1.getChildren().size() );
        assertTrue( descriptor1.getChildren().contains( descriptor2 ) );
    }

}
