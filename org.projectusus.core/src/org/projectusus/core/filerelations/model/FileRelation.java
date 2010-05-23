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
    private boolean obsolete;

    public static void clear() {
        relations = HashMultimap.create();
    }

    public static FileRelation of( ClassDescriptor source, ClassDescriptor target ) {
        Set<FileRelation> relationSet = relations.get( source );
        for( FileRelation relation : relationSet ) {
            if( relation.getTargetDescriptor().equals( target ) ) {
                return relation;
            }
        }
        FileRelation fileRelation = new FileRelation( source, target );
        relations.put( source, fileRelation );
        return fileRelation;
    }

    private FileRelation( ClassDescriptor source, ClassDescriptor target ) {
        this.source = source;
        this.target = target;
        source.addOutgoingRelation( this );
        target.addIncomingRelation( this );
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

    public static Set<FileRelation> getAllRelations() {
        Set<FileRelation> result = new HashSet<FileRelation>();
        result.addAll( relations.values() );
        return result;
    }

    public void remove() {
        source.removeOutgoingRelation( this );
        target.removeIncomingRelation( this );
        relations.remove( source, this );
    }

    // //////////

    // public static void markAndRemoveAllRelationsStartingAt( IFile file ) {
    // Set<FileRelation> removedRelations = outgoingRelations.removeAll( file );
    // for( FileRelation relation : removedRelations ) {
    // relation.markAsObsolete();
    // incomingRelations.remove( relation.getTargetFile(), relation );
    // }
    // }
    //
    // public static void registerAllRelationsEndingAt( IFile file ) {
    // registeredForRepair.addAll( incomingRelations.get( file ) );
    // }
}
