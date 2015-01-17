package org.projectusus.ui.dependencygraph;

import static java.util.Collections.singleton;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jface.viewers.ViewerFilter;
import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.SourceFolderFilter;
import org.projectusus.ui.dependencygraph.nodes.ClassRepresenter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = ClassGraphView.class.getName();

    private static final String ONLY_CROSS_PACKAGE = "Only cross-package relations";

    private static final DependencyGraphModel classGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return ClassRepresenter.getAllClassRepresenters();
        }
    };

    public ClassGraphView() {
        super( classGraphModel );
    }

    @Override
    protected Collection<? extends ViewerFilter> createAdditionalFilters() {
        return singleton( new SourceFolderFilter( "src/main/java" ) );
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
