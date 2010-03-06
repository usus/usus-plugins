package org.projectusus.core.filerelations.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.FileRelation;

public class FileRelations extends Relations<IFile, FileRelation> {

    public Set<FileRelation> getTransitiveRelationsFrom( IFile file, Classname clazz ) {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsFrom( file, clazz, transitives );
        return transitives;
    }

    public void add( FileRelation relation ) {
        add( relation, relation.getSourceFile(), relation.getTargetFile() );
    }

    private void getTransitiveRelationsFrom( IFile file, Classname clazz, Set<FileRelation> transitives ) {
        for( FileRelation relation : outgoingRelations.get( file ) ) {
            if( relation.hasSourceClass( clazz ) && transitives.add( relation ) ) {
                getTransitiveRelationsFrom( relation.getTargetFile(), relation.getTargetClassname(), transitives );
            }
        }
    }

}
