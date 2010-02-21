package org.projectusus.core.filerelations;

import static java.util.Collections.singleton;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.EdgeFactory;

class RelationGraph<T> extends UnmodifiableDirectedGraph<T, Relation<T>> {

    private final Relations<T, Relation<T>> relations;

    public RelationGraph( Relations<T, Relation<T>> relations ) {
        this.relations = relations;
    }

    public Set<Relation<T>> incomingEdgesOf( T vertex ) {
        return relations.getDirectRelationsTo( vertex );
    }

    public Set<Relation<T>> outgoingEdgesOf( T vertex ) {
        return relations.getDirectRelationsFrom( vertex );
    }

    public boolean containsVertex( T vertex ) {
        return relations.containsKey( vertex );
    }

    public Set<Relation<T>> edgeSet() {
        return new HashSet<Relation<T>>( relations.getAllDirectRelations() );
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
        return relations.keySet();
    }

}
