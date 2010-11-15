package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = ClassGraphView.class.getName();

    private static final String ONLY_CROSS_PACKAGE = "Only with cross-package relations";

    public ClassGraphView() {
        super( new ClassGraphModel() );
    }

    @Override
    protected int calcMaxFilterValue( int maxFilterValue ) {
        return maxFilterValue;
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-class-graph";
    }

    @Override
    protected String getCheckboxLabelName() {
        return ONLY_CROSS_PACKAGE;
    }
}
