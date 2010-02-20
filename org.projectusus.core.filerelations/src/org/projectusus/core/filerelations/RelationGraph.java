package org.projectusus.core.filerelations;

import static java.util.Collections.EMPTY_SET;
import static java.util.Collections.singleton;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractGraph;

import com.google.common.collect.SetMultimap;

public class RelationGraph<T> extends AbstractGraph<T, Relation<T>> implements DirectedGraph<T, Relation<T>> {

    private final SetMultimap<T, Relation<T>> outgoingRelations;
    private final SetMultimap<T, Relation<T>> incomingRelations;

    public RelationGraph( SetMultimap<T, Relation<T>> outgoingRelations, SetMultimap<T, Relation<T>> incomingRelations ) {
        this.outgoingRelations = outgoingRelations;
        this.incomingRelations = incomingRelations;
    }

    public int inDegreeOf( T vertex ) {
        return incomingEdgesOf( vertex ).size();
    }

    public Set<Relation<T>> incomingEdgesOf( T vertex ) {
        return incomingRelations.get( vertex );
    }

    public int outDegreeOf( T vertex ) {
        return outgoingEdgesOf( vertex ).size();
    }

    public Set<Relation<T>> outgoingEdgesOf( T vertex ) {
        return outgoingRelations.get( vertex );
    }

    public Relation<T> addEdge( T source, T target ) {
        throw newUnsupportedOperationException();
    }

    public boolean addEdge( T source, T target, Relation<T> relation ) {
        throw newUnsupportedOperationException();
    }

    public boolean addVertex( T vertex ) {
        throw newUnsupportedOperationException();
    }

    public boolean containsEdge( Relation<T> relation ) {
        return containsEdge( relation, relation.getSource() );
    }

    private boolean containsEdge( Relation<T> relation, T source ) {
        return outgoingEdgesOf( source ).contains( relation );
    }

    @Override
    public boolean containsEdge( T source, T target ) {
        return containsEdge( Relation.of( source, target ), source );
    }

    public boolean containsVertex( T vertex ) {
        return outgoingRelations.containsKey( vertex );
    }

    public Set<Relation<T>> edgeSet() {
        return new HashSet<Relation<T>>( outgoingRelations.values() );
    }

    public Set<Relation<T>> edgesOf( T vertex ) {
        Set<Relation<T>> result = new HashSet<Relation<T>>();
        result.addAll( outgoingEdgesOf( vertex ) );
        result.addAll( incomingEdgesOf( vertex ) );
        return result;
    }

    @SuppressWarnings( "unchecked" )
    public Set<Relation<T>> getAllEdges( T source, T target ) {
        Relation<T> relation = getEdge( source, target );
        return relation == null ? EMPTY_SET : singleton( relation );
    }

    public Relation<T> getEdge( T source, T target ) {
        if( containsEdge( source, target ) ) {
            return Relation.of( source, target );
        }
        return null;
    }

    public EdgeFactory<T, Relation<T>> getEdgeFactory() {
        return new EdgeFactory<T, Relation<T>>() {
            public Relation<T> createEdge( T source, T target ) {
                return Relation.of( source, target );
            }
        };
    }

    public T getEdgeSource( Relation<T> relation ) {
        return relation.getSource();
    }

    public T getEdgeTarget( Relation<T> relation ) {
        return relation.getTarget();
    }

    public double getEdgeWeight( Relation<T> T ) {
        return 0;
    }

    @Override
    public boolean removeAllEdges( Collection<? extends Relation<T>> relations ) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Set<Relation<T>> removeAllEdges( T source, T target ) {
        throw newUnsupportedOperationException();
    }

    @Override
    public boolean removeAllVertices( Collection<? extends T> vertices ) {
        throw newUnsupportedOperationException();
    }

    public boolean removeEdge( Relation<T> relation ) {
        throw newUnsupportedOperationException();
    }

    public Relation<T> removeEdge( T source, T target ) {
        throw newUnsupportedOperationException();
    }

    public boolean removeVertex( T vertex ) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException( "graph is not modifiable" ); //$NON-NLS-1$
    }

    public Set<T> vertexSet() {
        return outgoingRelations.keySet();
    }

}
