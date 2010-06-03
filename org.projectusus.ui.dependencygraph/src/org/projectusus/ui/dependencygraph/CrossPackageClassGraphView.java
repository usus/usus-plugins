package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class CrossPackageClassGraphView extends DependencyGraphView {

    private static final String ALL_CLASSES = "all cross-package classes";
    private static final String ONLY_IN_CYCLES = "only with cyclic dependencies";

    public CrossPackageClassGraphView() {
        super( new CrossPackageClassGraphModel() );
    }

    @Override
    protected String getScaleLeftLabelText() {
        return ALL_CLASSES;
    }

    @Override
    protected String getScaleRightLabelText() {
        return ONLY_IN_CYCLES;
    }
}
