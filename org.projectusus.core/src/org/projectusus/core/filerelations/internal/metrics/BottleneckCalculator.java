package org.projectusus.core.filerelations.internal.metrics;

import org.projectusus.core.filerelations.model.ClassDescriptor;

public class BottleneckCalculator {

    public static int getBottleneckCount( ClassDescriptor descriptor ) {
        return descriptor.getTransitiveRelationsFrom().size() * descriptor.getTransitiveRelationsTo().size();
    }

}
