package org.projectusus.core.filerelations.internal.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.Relation;

public class FileRelations extends Relations<IFile, FileRelation> {

    private Set<FileRelation> registeredForRepair = new HashSet<FileRelation>();

    public void add( FileRelation relation ) {
        add( relation, relation.getSourceFile(), relation.getTargetFile() );
    }

    public Set<FileRelation> getTransitiveRelationsFrom( IFile file, Classname classname ) {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsFrom( file, classname, transitives );
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

    private void getTransitiveRelationsFrom( IFile file, Classname clazz, Set<FileRelation> transitives ) {
        for( FileRelation relation : getOutgoingRelationsFrom( file ) ) {
            if( relation.hasSourceClass( clazz ) && transitives.add( relation ) ) {
                getTransitiveRelationsFrom( relation.getTargetFile(), relation.getTargetClassname(), transitives );
            }
        }
    }

    public Set<FileRelation> getTransitiveRelationsTo( IFile file, Classname classname ) {
        Set<FileRelation> transitives = new HashSet<FileRelation>();
        getTransitiveRelationsTo( file, classname, transitives );
        return transitives;
    }

    private void getTransitiveRelationsTo( IFile file, Classname classname, Set<FileRelation> transitives ) {
        for( FileRelation relation : getIncomingRelationsTo( file ) ) {
            if( relation.hasTargetClass( classname ) && transitives.add( relation ) ) {
                getTransitiveRelationsTo( relation.getSourceFile(), relation.getSourceClassname(), transitives );
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
        System.out.println( "Found non-obsolete relation: " + relation );
        if( !relation.isObsolete() ) {
            relationsToRepair.add( relation );
        }
    }

    public void remove( FileRelation relation ) {
        remove( relation, relation.getSourceFile(), relation.getTargetFile() );
    }

    // //// neues Objekt machen!! PackageRelation, PackageRelations

    public Relations<Packagename, Relation<Packagename>> calcPackageRelations() {
        Relations<Packagename, Relation<Packagename>> packageRelations = new Relations<Packagename, Relation<Packagename>>();
        for( FileRelation fileRelation : getAllDirectRelations() ) {
            if( fileRelation.isCrossPackage() ) {
                Packagename source = fileRelation.getSourcePackage();
                Packagename target = fileRelation.getTargetPackage();
                packageRelations.add( Relation.of( source, target ), source, target );
            }
        }
        return packageRelations;
    }

    public Set<Packagename> getDirectPackageRelationsFrom( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( Relation<Packagename> relation : calcPackageRelations().getDirectRelationsFrom( packagename ) ) {
            descriptors.add( relation.getTarget() );
        }
        return descriptors;
    }
}
