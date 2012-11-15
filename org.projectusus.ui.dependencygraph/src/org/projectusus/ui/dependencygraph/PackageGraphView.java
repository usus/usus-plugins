package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.internal.proportions.rawdata.UsusModelProvider;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class PackageGraphView extends DependencyGraphView {

    public static final String VIEW_ID = PackageGraphView.class.getName();

    private static final String ONLY_IN_CYCLES = "Only cyclic dependencies";

    private static final DependencyGraphModel packageGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return UsusModelProvider.getAllPackages();
        }
    };

    public PackageGraphView() {
        super( packageGraphModel );
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-package-graph";
    }

    @Override
    protected String getCheckboxLabelName() {
        return ONLY_IN_CYCLES;
    }
}
