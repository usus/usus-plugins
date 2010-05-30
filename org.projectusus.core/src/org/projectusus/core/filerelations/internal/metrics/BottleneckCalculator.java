package org.projectusus.core.filerelations.internal.metrics;

import org.projectusus.core.filerelations.model.ClassDescriptor;

public class BottleneckCalculator {

    public static int getBottleneckCount( ClassDescriptor descriptor ) {
        // do not account for "this"
        return (descriptor.getTransitiveParentCount() - 1) * (descriptor.getCCD() - 1);
    }

}
