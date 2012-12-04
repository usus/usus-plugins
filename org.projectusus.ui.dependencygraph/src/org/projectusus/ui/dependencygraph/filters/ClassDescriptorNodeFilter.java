package org.projectusus.ui.dependencygraph.filters;

import java.util.Collection;
import java.util.Set;

import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.ui.dependencygraph.nodes.ClassRepresenter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class ClassDescriptorNodeFilter extends NodeAndEdgeFilter {

    private Collection<GraphNode> classes;

    @Override
    public String getDescription() {
        return "Classes belonging to the selected hotspot";
    }

    @Override
    protected boolean select( GraphNode node, Set<GraphNode> others ) {
        return selectedByClasses( node );
    }

    private boolean selectedByClasses( GraphNode node ) {
        return classes == null || classes.contains( node );
    }

    public void setClasses( Set<ClassDescriptor> classDescriptors ) {
        classes = ClassRepresenter.transformToRepresenterSet( classDescriptors );
    }

}
