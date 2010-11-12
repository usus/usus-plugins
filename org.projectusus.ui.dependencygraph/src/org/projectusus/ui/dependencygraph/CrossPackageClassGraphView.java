package org.projectusus.ui.dependencygraph;

import org.eclipse.swt.widgets.Composite;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class CrossPackageClassGraphView extends DependencyGraphView {

    public CrossPackageClassGraphView() {
        super( new CrossPackageClassGraphModel() );
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-cross-package-class-graph";
    }

    @Override
    protected void createAdditionalWidget( Composite filterArea ) {
        setFilterLimit( 0 );
    }

    @Override
    protected void updateAdditionalWidget() {
        // we do not have one
    }
}
