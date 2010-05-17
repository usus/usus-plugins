package org.projectusus.core.filerelations.internal.metrics;

import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class BottleneckCalculator {

    private final FileRelations relations;

    public BottleneckCalculator( FileRelations relations ) {
        this.relations = relations;
    }

    public int getBottleneckCount( ClassDescriptor descriptor ) {
        return relations.getTransitiveRelationsFrom( descriptor ).size() * relations.getTransitiveRelationsTo( descriptor ).size();
    }

}
