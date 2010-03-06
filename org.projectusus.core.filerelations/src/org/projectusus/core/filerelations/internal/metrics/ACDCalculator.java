package org.projectusus.core.filerelations.internal.metrics;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;

public class ACDCalculator {

    private final FileRelations relations;

    public ACDCalculator( FileRelations relations ) {
        this.relations = relations;
    }

    public int getCCD( ClassDescriptor descriptor ) {
        Set<ClassDescriptor> descriptors = new HashSet<ClassDescriptor>();
        descriptors.add( descriptor );
        Set<FileRelation> classRelations = relations.getTransitiveRelationsFrom( descriptor.getFile(), descriptor.getClassname() );
        for( FileRelation relation : classRelations ) {
            descriptors.add( relation.getSourceDescriptor() );
            descriptors.add( relation.getTargetDescriptor() );
        }
        return descriptors.size();
    }

}
