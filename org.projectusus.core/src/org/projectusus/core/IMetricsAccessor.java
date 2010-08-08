package org.projectusus.core;

import java.util.Set;

import org.projectusus.core.basis.GraphNode;

public interface IMetricsAccessor {

    Set<GraphNode> getAllClassRepresenters();

    Set<GraphNode> getAllPackages();

    Set<GraphNode> getAllCrossPackageClasses();

    void acceptAndGuide( IMetricsResultVisitor visitor );
}
