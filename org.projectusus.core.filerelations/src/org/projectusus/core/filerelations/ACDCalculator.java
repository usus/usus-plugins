package org.projectusus.core.filerelations;

import java.util.HashSet;
import java.util.Set;

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
