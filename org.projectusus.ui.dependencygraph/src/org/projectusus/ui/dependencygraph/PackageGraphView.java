package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class PackageGraphView extends DependencyGraphView {

    public PackageGraphView() {
        super( new PackageGraphModel() );
    }
}
