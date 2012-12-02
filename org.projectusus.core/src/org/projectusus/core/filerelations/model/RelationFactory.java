package org.projectusus.core.filerelations.model;

import org.jgrapht.EdgeFactory;

public class RelationFactory implements EdgeFactory<ClassDescriptor, Relation<ClassDescriptor>> {

    public Relation<ClassDescriptor> createEdge( ClassDescriptor source, ClassDescriptor target ) {
        return new Relation<ClassDescriptor>( source, target );
    }
}
