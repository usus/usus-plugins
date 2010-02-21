package org.projectusus.core.filerelations;

import static com.google.common.collect.Sets.union;

import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.AbstractGraph;

abstract class UnmodifiableDirectedGraph<V, E> extends AbstractGraph<V, E> implements DirectedGraph<V, E> {

    public E addEdge( V source, V target ) {
        throw newUnsupportedOperationException();
    }

    public boolean addEdge( V source, V target, E edge ) {
        throw newUnsupportedOperationException();
    }

    public boolean addVertex( V vertex ) {
        throw newUnsupportedOperationException();
    }

    public boolean removeEdge( E edge ) {
        throw newUnsupportedOperationException();
    }

    public E removeEdge( V source, V target ) {
        throw newUnsupportedOperationException();
    }

    public boolean removeVertex( V vertex ) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException( "graph is not modifiable" ); //$NON-NLS-1$
    }

    public int inDegreeOf( V vertex ) {
        return incomingEdgesOf( vertex ).size();
    }

    public int outDegreeOf( V vertex ) {
        return outgoingEdgesOf( vertex ).size();
    }

    public Set<E> edgesOf( V vertex ) {
        return union( outgoingEdgesOf( vertex ), incomingEdgesOf( vertex ) );
    }

    public double getEdgeWeight( E edge ) {
        return 0;
    }

    public boolean containsEdge( E edge ) {
        return containsEdge( getEdgeSource( edge ), getEdgeTarget( edge ) );
    }

}
