package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.GraphNode;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;

public class ClassGraphModel extends DependencyGraphModel {

    public ClassGraphModel() {
        super();
    }

    @Override
    protected Set<? extends GraphNode> getRefreshedNodes() {
        return UsusCorePlugin.getUsusModel().getAllClassRepresenters();
    }

}
