package org.projectusus.core.filerelations.model;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.c4j.C4JFileWriter;
import org.projectusus.c4j.UsusContractBase;

public class ClassDescriptorContract extends UsusContractBase<ClassDescriptor> {

    private String targetString() {
        return " Meth-Target: " + m_target;
    }

    public ClassDescriptorContract( ClassDescriptor target ) {
        super( target );
    }

    public void classInvariant() {
        assertThat( ClassDescriptor.getAll().contains( m_target ), "Element is contained in list of all:" + targetString() );

        for( ClassDescriptor child : m_target.getChildren() ) {
            assertThat( child.getParents().contains( m_target ), "Target is parent of each child" + targetString() + " child: " + child );
        }

        for( ClassDescriptor parent : m_target.getParents() ) {
            assertThat( parent.getChildren().contains( m_target ), "Target is child of each parent" + targetString() + " parent: " + parent );
        }
    }

    public static void pre_clear() {
        // TODO no pre-condition identified yet
    }

    public static void post_clear() {
        C4JFileWriter.assertStatic( ClassDescriptor.getAll().isEmpty(), "Set of all class descriptors is empty" );
    }

    public static void pre_getAll() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAll() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_of( WrappedTypeBinding type ) {
        C4JFileWriter.assertStatic( type != null, "type_not_null" );
        C4JFileWriter.assertStatic( type.isValid(), "Wrapped type must be valid" );
        C4JFileWriter.assertStatic( type.getUnderlyingResource() != null, "Underlying Resource of type must not be null." );
        C4JFileWriter.assertStatic( type.getClassname() != null, "Classname of type must not be null" );
        C4JFileWriter.assertStatic( type.getPackagename() != null, "Packagename of type must not be null" );
    }

    public static void post_of( WrappedTypeBinding type ) {
        ClassDescriptor returnValue = (ClassDescriptor)getReturnValue();
        C4JFileWriter.assertStatic( ClassDescriptor.getAll().contains( returnValue ), "List of all contains ClassDescriptor " + returnValue );
    }

    public static void pre_of( IFile file, Classname classname, Packagename packagename ) {
        C4JFileWriter.assertStatic( file != null, "file_not_null" );
        C4JFileWriter.assertStatic( classname != null, "classname_not_null" );
        C4JFileWriter.assertStatic( packagename != null, "packagename_not_null" );
    }

    public static void post_of( IFile file, Classname classname, Packagename packagename ) {
        ClassDescriptor returnValue = (ClassDescriptor)getReturnValue();
        C4JFileWriter.assertStatic( ClassDescriptor.getAll().contains( returnValue ), "List of all contains ClassDescriptor " + returnValue );
        String classtext = " Class: " + returnValue;
        C4JFileWriter.assertStatic( returnValue.getFile() == file, "Created class is associated with file." + classtext + " File: " + file );
        C4JFileWriter.assertStatic( returnValue.getClassname() == classname, "Created class is associated with classname." + classtext + " Classname: " + classname );
        C4JFileWriter.assertStatic( returnValue.getPackagename() == packagename, "Created class is associated with packagename." + classtext + " Package: " + packagename );
        C4JFileWriter.assertStatic( packagename.containsClass( returnValue ), "Package of created class contains this class." + classtext + " Package: " + packagename );
    }

    public void pre_getFile() {
        // TODO no pre-condition identified yet
    }

    public void post_getFile() {
        IFile returnValue = (IFile)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_getClassname() {
        // TODO no pre-condition identified yet
    }

    public void post_getClassname() {
        Classname returnValue = (Classname)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_getPackagename() {
        // TODO no pre-condition identified yet
    }

    public void post_getPackagename() {
        Packagename returnValue = (Packagename)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_qualifiedClassName() {
        // TODO no pre-condition identified yet
    }

    public void post_qualifiedClassName() {
        String returnValue = (String)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_getChildren() {
        // TODO no pre-condition identified yet
    }

    public void post_getChildren() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        assertThat( !returnValue.contains( m_target ), "Target is not a child" + targetString() );
    }

    public void pre_getParents() {
        // TODO no pre-condition identified yet
    }

    public void post_getParents() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        assertThat( !returnValue.contains( m_target ), "Target is not a parent" + targetString() );
    }

    public void pre_getChildrenInOtherPackages() {
        // TODO no pre-condition identified yet
    }

    public void post_getChildrenInOtherPackages() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        assertThat( !returnValue.contains( m_target ), "Target is not a child in another package" + targetString() );
        for( ClassDescriptor descriptor : returnValue ) {
            assertThat( !descriptor.getPackagename().equals( m_target.getPackagename() ), "Children are not in the same package as target" + targetString() );
        }
    }

    public void pre_getCCD() {
        // TODO no pre-condition identified yet
    }

    public void post_getCCD() {
        int returnValue = ((Integer)getReturnValue()).intValue();
        // TODO no post-condition identified yet
    }

    public void pre_getTransitiveParentCount() {
        // TODO no pre-condition identified yet
    }

    public void post_getTransitiveParentCount() {
        int returnValue = ((Integer)getReturnValue()).intValue();
        // TODO no post-condition identified yet
    }

    public void pre_prepareRemoval() {
        // TODO no pre-condition identified yet
    }

    public void post_prepareRemoval() {
        assertThat( m_target.getChildren().isEmpty(), "Target is not connected to any children any more" + targetString() );
        assertThat( m_target.getCCD() == 1, "Target's CCD is 1" + targetString() );
        for( ClassDescriptor child : ClassDescriptor.getAll() ) {
            assertThat( !child.getParents().contains( m_target ), "There are no children for target any more" + targetString() + " Child: " + child );
        }

    }

    public void pre_removeFromPool() {
        post_prepareRemoval();
    }

    public void post_removeFromPool() {
        post_prepareRemoval();
        assertThat( m_target.getParents().isEmpty(), "Target is not connected to any parents any more" + targetString() );
        assertThat( m_target.getTransitiveParentCount() == 1, "Target's transitive parent count is 1" + targetString() );
        for( ClassDescriptor parent : ClassDescriptor.getAll() ) {
            assertThat( !parent.getChildren().contains( m_target ), "There are no parents for target any more" + targetString() + " Parent: " + parent );
        }
    }

    public void pre_addChild( ClassDescriptor target ) {
        assertThat( target != null, "target_not_null" + targetString() );
    }

    public void post_addChild( ClassDescriptor target ) {
        if( !m_target.equals( target ) ) {
            assertThat( m_target.getChildren().contains( target ), "Method argument is now in set of children" + targetString() );
        }
    }

    public void pre_getParentsInOtherPackages() {
        // TODO no pre-condition identified yet
    }

    public void post_getParentsInOtherPackages() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        assertThat( !returnValue.contains( m_target ), "Target is not a parent in another package" + targetString() );
        for( ClassDescriptor descriptor : returnValue ) {
            assertThat( !descriptor.getPackagename().equals( m_target.getPackagename() ), "Parents are not in the same package as target" + targetString() );
        }
    }
}
