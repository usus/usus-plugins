package org.projectusus.core.filerelations;

import static com.google.common.collect.Sets.union;
import static java.util.Collections.singleton;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.EdgeFactory;

import com.google.common.collect.SetMultimap;

class RelationGraph<T> extends UnmodifiableDirectedGraph<T, Relation<T>> {

    private final SetMultimap<T, Relation<T>> outgoingRelations;
    private final SetMultimap<T, Relation<T>> incomingRelations;

    public RelationGraph( SetMultimap<T, Relation<T>> outgoingRelations, SetMultimap<T, Relation<T>> incomingRelations ) {
        this.outgoingRelations = outgoingRelations;
        this.incomingRelations = incomingRelations;
    }

    public Set<Relation<T>> incomingEdgesOf( T vertex ) {
        return incomingRelations.get( vertex );
    }

    public Set<Relation<T>> outgoingEdgesOf( T vertex ) {
        return outgoingRelations.get( vertex );
    }

    public boolean containsVertex( T vertex ) {
        return outgoingRelations.containsKey( vertex ) || incomingRelations.containsKey( vertex );
    }

    public Set<Relation<T>> edgeSet() {
        return new HashSet<Relation<T>>( outgoingRelations.values() );
    }

    public Set<Relation<T>> getAllEdges( T source, T target ) {
        Relation<T> relation = getEdge( source, target );
        Set<Relation<T>> emptySet = Collections.<Relation<T>> emptySet();
        return relation == null ? emptySet : singleton( relation );
    }

    public Relation<T> getEdge( T source, T target ) {
        Relation<T> searchedForRelation = Relation.of( source, target );
        for( Relation<T> relation : outgoingEdgesOf( source ) ) {
            if( searchedForRelation.equals( relation ) ) {
                return relation;
            }
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

    public Set<T> vertexSet() {
        return union( outgoingRelations.keySet(), incomingRelations.keySet() );
    }

}
