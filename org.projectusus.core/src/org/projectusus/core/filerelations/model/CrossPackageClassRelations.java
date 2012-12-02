package org.projectusus.core.filerelations.model;

import org.jgrapht.alg.StrongConnectivityInspector;

public class CrossPackageClassRelations extends Relations<ClassDescriptor> {

    private ClassCycles classCycles;

    public CrossPackageClassRelations() {
        calcClassRelations();
        calcClassCycles();
    }

    private void calcClassRelations() {
        for( ClassDescriptor source : ClassDescriptor.getAll() ) {
            for( ClassDescriptor target : source.getChildrenInOtherPackages() ) {
                this.add( source, target );
            }
        }
    }

    private void calcClassCycles() {
        RelationGraph<ClassDescriptor> graph = new RelationGraph<ClassDescriptor>( this );
        StrongConnectivityInspector<ClassDescriptor, Relation<ClassDescriptor>> inspector = new StrongConnectivityInspector<ClassDescriptor, Relation<ClassDescriptor>>( graph );
        classCycles = new ClassCycles( inspector.stronglyConnectedSets() );
    }

    public ClassCycles getCrossPackageClassCycles() {
        return classCycles;
    }
}
