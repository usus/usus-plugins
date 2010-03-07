package org.projectusus.core.filerelations;

import java.util.Set;

import org.projectusus.core.filerelations.internal.metrics.ACDCalculator;
import org.projectusus.core.filerelations.internal.model.FileRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;

public class FileRelationMetrics {

    private final FileRelations relations;

    public FileRelationMetrics( FileRelations relations ) {
        this.relations = relations;
    }

    public FileRelationMetrics() {
        this( new FileRelations() );
    }

    public void addFileRelation( ClassDescriptor source, ClassDescriptor target ) {
        relations.add( new FileRelation( source, target ) );
    }

    public int getCCD( ClassDescriptor descriptor ) {
        return new ACDCalculator( relations ).getCCD( descriptor );
    }

    public Set<ClassDescriptor> getChildren( ClassDescriptor descriptor ) {
        return relations.getDirectRelationsFrom( descriptor );
    }

}
