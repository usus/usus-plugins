package org.projectusus.core.filerelations.internal.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;

public class FileRelations extends Relations<IFile, FileRelation> {

    private Set<FileRelation> registeredForRepair = new HashSet<FileRelation>();

    public void add( FileRelation relation ) {
        add( relation, relation.getSourceFile(), relation.getTargetFile() );
    }

    public Set<FileRelation> getTransitiveRelationsFrom( ClassDescriptor descriptor ) {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsFrom( descriptor, transitives );
        return transitives;
    }

    public Set<ClassDescriptor> getDirectRelationsFrom( ClassDescriptor descriptor ) {
        Set<ClassDescriptor> descriptors = new HashSet<ClassDescriptor>();
        for( FileRelation relation : getOutgoingRelationsFrom( descriptor.getFile() ) ) {
            if( relation.hasSourceClass( descriptor.getClassname() ) ) {
                descriptors.add( relation.getTargetDescriptor() );
            }
        }
        return descriptors;
    }

    private void getTransitiveRelationsFrom( ClassDescriptor descriptor, Set<FileRelation> transitives ) {
        for( FileRelation relation : getOutgoingRelationsFrom( descriptor.getFile() ) ) {
            if( relation.hasSourceClass( descriptor.getClassname() ) && transitives.add( relation ) ) {
                getTransitiveRelationsFrom( relation.getTarget(), transitives );
            }
        }
    }

    public Set<FileRelation> getTransitiveRelationsTo( ClassDescriptor descriptor ) {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsTo( descriptor, transitives );
        return transitives;
    }

    private void getTransitiveRelationsTo( ClassDescriptor descriptor, Set<FileRelation> transitives ) {
        for( FileRelation relation : getIncomingRelationsTo( descriptor.getFile() ) ) {
            if( relation.hasTargetClass( descriptor.getClassname() ) && transitives.add( relation ) ) {
                getTransitiveRelationsTo( relation.getSource(), transitives );
            }
        }
    }

    public void markAndRemoveAllRelationsStartingAt( IFile file ) {
        Set<FileRelation> removedRelations = outgoingRelations.removeAll( file );
        for( FileRelation relation : removedRelations ) {
            relation.markAsObsolete();
            incomingRelations.remove( relation.getTargetFile(), relation );
        }
    }

    public void registerAllRelationsEndingAt( IFile file ) {
        registeredForRepair.addAll( incomingRelations.get( file ) );
    }

    public List<FileRelation> extractRelationsRegisteredForRepair() {
        List<FileRelation> relationsToRepair = new ArrayList<FileRelation>();
        for( Iterator<FileRelation> iter = registeredForRepair.iterator(); iter.hasNext(); ) {
            addRelationToRepairList( relationsToRepair, iter.next() );
            iter.remove();
        }
        return relationsToRepair;
    }

    private void addRelationToRepairList( List<FileRelation> relationsToRepair, FileRelation relation ) {
        if( !relation.isObsolete() ) {
            relationsToRepair.add( relation );
        }
    }

    public void remove( FileRelation relation ) {
        remove( relation, relation.getSourceFile(), relation.getTargetFile() );
    }
}
