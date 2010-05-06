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

    // / The relative ACD of a system with n components is ACD/n.
    // / Thus it is a percentage value in range [0%, 100%].
    public double getRelativeACD() {
        int numberOfClasses = ClassDescriptor.getAll().size();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getACD() / numberOfClasses;
    }

    // / The average component dependency (ACD) of a system with n components is
    // CCD/n.
    private double getACD() {
        int numberOfClasses = ClassDescriptor.getAll().size();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getCCD() / (double)numberOfClasses;
    }

    // / The cumulative component dependency (CCD) of a (sub)system is the sum
    // over all
    // / components Ci of the (sub)system of the number of components needed to
    // test each Ci incrementally.
    private int getCCD() {
        int allDependencies = 0;
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            allDependencies += getCCD( descriptor );
        }
        return allDependencies;
    }
}
