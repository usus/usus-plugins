package org.projectusus.ui.dependencygraph;

import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class ClassGraphView extends DependencyGraphView {

    public ClassGraphView() {
        super( new ClassGraphModel(), true );
    }

}
