package org.projectusus.core.filerelations.model;

import java.util.Set;

import org.eclipse.core.resources.IFile;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class FileRelation extends Relation<ClassDescriptor> {

    private static SetMultimap<ClassDescriptor, FileRelation> relations = HashMultimap.create();

    private boolean obsolete;

    public static FileRelation of( ClassDescriptor source, ClassDescriptor target ) {
        Set<FileRelation> relationSet = relations.get( source );
        for( FileRelation relation : relationSet ) {
            if( relation.getTarget().equals( target ) ) {
                return relation;
            }
        }
        FileRelation fileRelation = new FileRelation( source, target );
        relations.put( source, fileRelation );
        return fileRelation;
    }

    private FileRelation( ClassDescriptor source, ClassDescriptor target ) {
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
