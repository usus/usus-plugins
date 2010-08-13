package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = ClassGraphView.class.getName();

    private static final String SCALE_LEFT_TEXT = "All";
    private static final String SCALE_RIGHT_TEXT = "None";

    public ClassGraphView() {
        super( new ClassGraphModel() );
    }

    @Override
    protected String getScaleRightLabelText() {
        return SCALE_RIGHT_TEXT;
    }

    @Override
    protected String getScaleLeftLabelText() {
        return SCALE_LEFT_TEXT;
    }

    @Override
    protected int calcMaxFilterValue( int maxFilterValue ) {
        return maxFilterValue + 1;
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-class-graph";
    }
}
