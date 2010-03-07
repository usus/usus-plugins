package org.projectusus.core.filerelations.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.FileRelation;

public class FileRelations extends Relations<IFile, FileRelation> {

    public void add( FileRelation relation ) {
        add( relation, relation.getSourceFile(), relation.getTargetFile() );
    }

    public Set<FileRelation> getTransitiveRelationsFrom( IFile file, Classname clazz ) {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsFrom( file, clazz, transitives );
        return transitives;
    }

    public Set<ClassDescriptor> getDirectRelationsFrom( ClassDescriptor descriptor ) {
        Set<ClassDescriptor> descriptors = new HashSet<ClassDescriptor>();
        for( FileRelation relation : getOutgoingRelations( descriptor.getFile() ) ) {
            if( relation.hasSourceClass( descriptor.getClassname() ) ) {
                descriptors.add( relation.getTargetDescriptor() );
            }
        }
        return descriptors;
    }

    private void getTransitiveRelationsFrom( IFile file, Classname clazz, Set<FileRelation> transitives ) {
        for( FileRelation relation : getOutgoingRelations( file ) ) {
            if( relation.hasSourceClass( clazz ) && transitives.add( relation ) ) {
                getTransitiveRelationsFrom( relation.getTargetFile(), relation.getTargetClassname(), transitives );
            }
        }
    }

    private Set<FileRelation> getOutgoingRelations( IFile file ) {
        return outgoingRelations.get( file );
    }

}
