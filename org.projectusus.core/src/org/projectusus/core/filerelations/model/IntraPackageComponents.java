package org.projectusus.core.filerelations.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;

import ch.akuhn.foreach.ForEach;
import ch.akuhn.foreach.GroupBy;

public class IntraPackageComponents {

    public List<Set<ClassDescriptor>> getComponents() {
        DirectedGraph<ClassDescriptor, Relation<ClassDescriptor>> graph = calcGraph();

        ConnectivityInspector<ClassDescriptor, Relation<ClassDescriptor>> inspector = new ConnectivityInspector<ClassDescriptor, Relation<ClassDescriptor>>( graph );

        return inspector.connectedSets();
    }

    public Map<Packagename, List<Set<ClassDescriptor>>> getSetsPerPackage() {
        return setsPerPackage( getComponents() );
    }

    static Map<Packagename, List<Set<ClassDescriptor>>> setsPerPackage( List<Set<ClassDescriptor>> sets ) {
        for( GroupBy<Set<ClassDescriptor>> set : ForEach.groupBy( sets ) ) {
            set.yield = set.value.iterator().next().getPackagename();
        }
        return ForEach.result();
    }

    private DirectedGraph<ClassDescriptor, Relation<ClassDescriptor>> calcGraph() {
        DirectedGraph<ClassDescriptor, Relation<ClassDescriptor>> graph = new DefaultDirectedGraph<ClassDescriptor, Relation<ClassDescriptor>>( new RelationFactory() );

        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            graph.addVertex( descriptor );
        }
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            for( ClassDescriptor child : descriptor.getChildrenInSamePackage() ) {
                graph.addEdge( descriptor, child );
            }
        }
        return graph;
    }
}
