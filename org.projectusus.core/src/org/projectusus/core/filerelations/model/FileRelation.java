package org.projectusus.core.filerelations.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class FileRelation {

    private static SetMultimap<ClassDescriptor, FileRelation> relations = HashMultimap.create();

    private ClassDescriptor source;
    private ClassDescriptor target;

    public static Set<FileRelation> getAllRelations() {
        return new HashSet<FileRelation>( relations.values() );
    }

    public static void clear() {
        relations = HashMultimap.create();
    }

    public static FileRelation of( ClassDescriptor source, ClassDescriptor target ) {
        for( FileRelation relation : relations.get( source ) ) {
            if( relation.getTargetDescriptor().equals( target ) ) {
                return relation;
            }
        }
        return newFileRelation( source, target );
    }

    private static FileRelation newFileRelation( ClassDescriptor source, ClassDescriptor target ) {
        FileRelation fileRelation = new FileRelation( source, target );
        relations.put( source, fileRelation );
        return fileRelation;
    }

    private FileRelation( ClassDescriptor source, ClassDescriptor target ) {
        this.source = source;
        this.target = target;
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

    public Packagename getSourcePackage() {
        return source.getPackagename();
    }

    public Packagename getTargetPackage() {
        return target.getPackagename();
    }

    public boolean isCrossPackage() {
        return !getSourcePackage().equals( getTargetPackage() );
    }

    public void remove() {
        relations.remove( source, this );
    }
}
