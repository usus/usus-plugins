package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;
import org.projectusus.c4j.UsusContractBase;

public class ClassDescriptorKeyContract extends UsusContractBase<ClassDescriptorKey> {

    public ClassDescriptorKeyContract( ClassDescriptorKey target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public void pre_ClassDescriptorKey( IFile file, Classname classname, Packagename packagename ) {
        // TODO Auto-generated pre-condition
        assertThat( file != null, "file_not_null" );
        assertThat( classname != null, "classname_not_null" );
        assertThat( packagename != null, "packagename_not_null" );
    }

    public void post_ClassDescriptorKey( IFile file, Classname classname, Packagename packagename ) {
        // TODO no post-condition identified yet
    }

    public void pre_ClassDescriptorKey( WrappedTypeBinding type ) {
        // TODO Auto-generated pre-condition
        assertThat( type != null, "type_not_null" );
        assertThat( type.isValid(), "Wrapped type must be valid" );
        assertThat( type.getUnderlyingResource() != null, "Underlying Resource of type must not be null." );
        assertThat( type.getClassname() != null, "Classname of type must not be null" );
        assertThat( type.getPackagename() != null, "Packagename of type must not be null" );
    }

    public void post_ClassDescriptorKey( WrappedTypeBinding type ) {
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

}
