package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;

public class PackageGraphModel extends DependencyGraphModel {

    @Override
    protected Set<? extends GraphNode> getRefreshedNodes() {
        return UsusModelProvider.getAllPackages();
    }

    @Override
    public int getMaxFilterValue() {
        return 1;
    }

}
