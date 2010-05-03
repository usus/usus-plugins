package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.GraphNode;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;

public class PackageGraphModel extends DependencyGraphModel {

    @Override
    protected Set<? extends GraphNode> getRefreshedNodes() {
        return UsusCorePlugin.getUsusModel().getAllPackages();
    }

    @Override
    public int getMaxFilterValue() {
        return 1;
    }

}
