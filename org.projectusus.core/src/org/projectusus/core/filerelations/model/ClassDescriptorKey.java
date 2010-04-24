package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;

class ClassDescriptorKey {
    public IFile file;
    public Classname classname;
    public Packagename packagename;

    public ClassDescriptorKey( IFile file, Classname classname, Packagename packagename ) {
        if( file == null || classname == null || packagename == null ) {
            throw new IllegalArgumentException( "Null parameters are not allowed." );
        }
        this.file = file;
        this.classname = classname;
        this.packagename = packagename;
    }
}
