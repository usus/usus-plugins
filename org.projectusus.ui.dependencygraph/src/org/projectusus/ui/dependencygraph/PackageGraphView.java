package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class PackageGraphView extends DependencyGraphView {

    public static final String VIEW_ID = PackageGraphView.class.getName();

    private static final String ONLY_IN_CYCLES = "only with cyclic dependencies";

    public PackageGraphView() {
        super( new PackageGraphModel() );
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
