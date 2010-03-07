package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;

public class FileRelation extends Relation<ClassDescriptor> {

    public FileRelation( ClassDescriptor source, ClassDescriptor target ) {
        super( source, target );
    }

    public IFile getSourceFile() {
        return source.getFile();
    }

    public IFile getTargetFile() {
        return target.getFile();
    }

    public Classname getSourceClassname() {
        return source.getClassname();
    }

    public Classname getTargetClassname() {
        return target.getClassname();
    }

    public ClassDescriptor getSourceDescriptor() {
        return source;
    }

    public ClassDescriptor getTargetDescriptor() {
        return target;
    }

    public boolean hasSourceClass( Classname clazz ) {
        return getSourceClassname().equals( clazz );
    }

    public Packagename getSourcePackage() {
        return source.getPackagename();
    }

    public Packagename getTargetPackage() {
        return target.getPackagename();
    }

    public boolean isCrossPackage() {
        return !getSourcePackage().equals( getTargetPackage() );
    }

}
