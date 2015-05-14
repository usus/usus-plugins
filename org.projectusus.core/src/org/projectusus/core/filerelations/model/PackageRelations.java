package org.projectusus.core.filerelations.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;

public class PackageRelations extends Relations<Packagename> {

    private PackageCycles packageCycles;

    public PackageRelations() {
        calcPackageRelations();
        calcPackageCycles();
    }

    private void calcPackageRelations() {
        for( ClassDescriptor source : ClassDescriptor.getAll() ) {
            for( ClassDescriptor target : source.getChildrenInOtherPackages() ) {
                this.add( source.getPackagename(), target.getPackagename() );
            }
        }
    }

    private void calcPackageCycles() {
        RelationGraph<Packagename> graph = new RelationGraph<Packagename>( this );
        StrongConnectivityInspector<Packagename, Relation<Packagename>> inspector = new StrongConnectivityInspector<Packagename, Relation<Packagename>>( graph );
        packageCycles = new PackageCycles( inspector.stronglyConnectedSets() );
    }

    public Set<Packagename> getDirectPackageRelationsFrom( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( Relation<Packagename> relation : this.getDirectRelationsFrom( packagename ) ) {
            descriptors.add( relation.getTarget() );
        }
        return descriptors;
    }

    public Set<Packagename> getDirectPackageRelationsTo( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( Relation<Packagename> relation : this.getDirectRelationsTo( packagename ) ) {
            descriptors.add( relation.getSource() );
        }
        return descriptors;
    }

    public PackageCycles getPackageCycles() {
        return packageCycles;
    }

    public int getCrossLinkCount( Packagename source, Packagename target ) {
        int crossLinkCount = 0;
        Collection<Relation<Packagename>> relationsFromSource = getAllOutgoingRelationsFrom( source );
        for( Relation<Packagename> relation : relationsFromSource ) {
            if( relation.getTarget().equals( target ) ) {
                crossLinkCount++;
            }
        }
        return crossLinkCount;
    }
}
