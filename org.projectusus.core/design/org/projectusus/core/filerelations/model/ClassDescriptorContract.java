package org.projectusus.core.filerelations.model;

import java.util.Set;

import net.sourceforge.c4j.ContractBase;

import org.eclipse.core.resources.IFile;

public class ClassDescriptorContract extends ContractBase<ClassDescriptor> {

    public ClassDescriptorContract( ClassDescriptor target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public static void pre_clear() {
        // TODO no pre-condition identified yet
    }

    public static void post_clear() {
        // TODO no post-condition identified yet
    }

    public static void pre_getAll() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAll() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_of( WrappedTypeBinding type ) {
        assert type != null : "type_not_null";
    }

    public static void post_of( WrappedTypeBinding type ) {
        ClassDescriptor returnValue = (ClassDescriptor)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_of( IFile file, Classname classname, Packagename packagename ) {
        assert file != null : "file_not_null";
        assert classname != null : "classname_not_null";
        assert packagename != null : "packagename_not_null";
    }

    public static void post_of( IFile file, Classname classname, Packagename packagename ) {
        ClassDescriptor returnValue = (ClassDescriptor)getReturnValue();
        // TODO no post-condition identified yet
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
        assert !returnValue.contains( m_target ) : "Target is not a child";
    }

    public void pre_getParents() {
        // TODO no pre-condition identified yet
    }

    public void post_getParents() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        assert !returnValue.contains( m_target ) : "Target is not a parent";
    }

    public void pre_getChildrenInOtherPackages() {
        // TODO no pre-condition identified yet
    }

    public void post_getChildrenInOtherPackages() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        for( ClassDescriptor descriptor : returnValue ) {
            assert !descriptor.getPackagename().equals( m_target.getPackagename() ) : "Children are not in the same package as target";
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
        // TODO no post-condition identified yet
    }

    public void pre_removeFromPool() {
        // TODO no pre-condition identified yet
    }

    public void post_removeFromPool() {
        // TODO no post-condition identified yet
    }

    public void pre_addChild( ClassDescriptor target ) {
        assert target != null : "target_not_null";
    }

    public void post_addChild( ClassDescriptor target ) {
        assert m_target.getChildren().contains( target ) : "Method argument is now in set of children";
    }

    public void pre_getParentsInOtherPackages() {
        // TODO no pre-condition identified yet
    }

    public void post_getParentsInOtherPackages() {
        Set<ClassDescriptor> returnValue = (Set<ClassDescriptor>)getReturnValue();
        for( ClassDescriptor descriptor : returnValue ) {
            assert !descriptor.getPackagename().equals( m_target.getPackagename() ) : "Parents are not in the same package as target";
        }
    }

}
