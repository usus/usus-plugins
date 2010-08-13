package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class PackageGraphView extends DependencyGraphView {

    public static final String VIEW_ID = PackageGraphView.class.getName();

    private static final String ALL_PACKAGES = "all packages";
    private static final String ONLY_IN_CYCLES = "only with cyclic dependencies";

    public PackageGraphView() {
        super( new PackageGraphModel() );
    }

    @Override
    protected String getScaleLeftLabelText() {
        return ALL_PACKAGES;
    }

    @Override
    protected String getScaleRightLabelText() {
        return ONLY_IN_CYCLES;
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-package-graph";
    }
}
