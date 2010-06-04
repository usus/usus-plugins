package org.projectusus.core.filerelations.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.Relation;

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

    public PackageCycles getPackageCycles() {
        return packageCycles;
    }
}
