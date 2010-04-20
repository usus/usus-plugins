package org.projectusus.core.filerelations.model;

import org.eclipse.core.resources.IFile;

public class FileRelation extends Relation<ClassDescriptor> {

    private boolean obsolete;

    public FileRelation( ClassDescriptor source, ClassDescriptor target ) {
        super( source, target );
        obsolete = false;
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

    public boolean hasTargetClass( Classname clazz ) {
        return getTargetClassname().equals( clazz );
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

    public void markAsObsolete() {
        obsolete = true;
    }

    public boolean isObsolete() {
        return obsolete;
    }

}
