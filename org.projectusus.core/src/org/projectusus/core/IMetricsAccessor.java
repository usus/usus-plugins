package org.projectusus.core;

import java.util.Set;

import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.statistics.MetricsResultVisitor;

public interface IMetricsAccessor {

    Set<GraphNode> getAllClassRepresenters();

    Set<GraphNode> getAllPackages();

    Set<GraphNode> getAllCrossPackageClasses();

    void acceptAndGuide( MetricsResultVisitor visitor );

    double getRelativeACD(); // TODO wo soll die sein?

}
