package org.projectusus.core.filerelations.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.PackageRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageRelations extends Relations<Packagename, PackageRelation> {

    private PackageCycles packageCycles;

    public PackageRelations() {
        calcPackageRelations();
        calcPackageCycles();
    }

    private void calcPackageRelations() {
        for( ClassDescriptor source : ClassDescriptor.getAll() ) {
            for( ClassDescriptor target : source.getChildrenInOtherPackages() ) {
                this.add( new PackageRelation( source.getPackagename(), target.getPackagename() ), source.getPackagename(), target.getPackagename() );
            }
        }
    }

    private void calcPackageCycles() {
        RelationGraph<Packagename, PackageRelation> graph = new RelationGraph<Packagename, PackageRelation>( this );
        StrongConnectivityInspector<Packagename, PackageRelation> inspector = new StrongConnectivityInspector<Packagename, PackageRelation>( graph );
        packageCycles = new PackageCycles( inspector.stronglyConnectedSets() );
    }

    public Set<Packagename> getDirectPackageRelationsFrom( Packagename packagename ) {
        Set<Packagename> descriptors = new HashSet<Packagename>();
        for( PackageRelation relation : this.getDirectRelationsFrom( packagename ) ) {
            descriptors.add( relation.getTarget() );
        }
        return descriptors;
    }

    public PackageCycles getPackageCycles() {
        return packageCycles;
    }
}
