package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;

public class CrossPackageClassGraphModel extends DependencyGraphModel {

    @Override
    protected Set<? extends GraphNode> getRefreshedNodes() {
        return UsusCorePlugin.getMetricsAccessor().getAllCrossPackageClasses();
    }

    @Override
    public int getMaxFilterValue() {
        return 1;
    }

}
