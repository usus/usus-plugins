package org.projectusus.core.filerelations.internal.model;

import static java.util.Collections.singleton;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.projectusus.core.filerelations.model.PackageRelation;
import org.projectusus.core.filerelations.model.Packagename;

public class RelationGraph<T extends Packagename, TRelation extends PackageRelation> extends UnmodifiableDirectedGraph<T, TRelation> {

    private final Relations<T, TRelation> relations;

    public RelationGraph( Relations<T, TRelation> relations ) {
        this.relations = relations;
    }

    public Set<TRelation> incomingEdgesOf( T vertex ) {
        return relations.getDirectRelationsTo( vertex );
    }

    public Set<TRelation> outgoingEdgesOf( T vertex ) {
        return relations.getDirectRelationsFrom( vertex );
    }

    public boolean containsVertex( T vertex ) {
        return relations.containsKey( vertex );
    }

    public Set<TRelation> edgeSet() {
        return new HashSet<TRelation>( relations.getAllDirectRelations() );
    }

    public Set<TRelation> getAllEdges( T source, T target ) {
        TRelation relation = getEdge( source, target );
        Set<TRelation> emptySet = Collections.<TRelation> emptySet();
        return relation == null ? emptySet : singleton( relation );
    }

    public TRelation getEdge( T source, T target ) {
        TRelation searchedForRelation = (TRelation)source.getRelationTo( target );// Relation.of( source, target );
        for( TRelation relation : outgoingEdgesOf( source ) ) {
            if( searchedForRelation.equals( relation ) ) {
                return relation;
            }
        }
        return null;
    }

    public EdgeFactory<T, TRelation> getEdgeFactory() {
        return new EdgeFactory<T, TRelation>() {
            public TRelation createEdge( T source, T target ) {
                return (TRelation)source.getRelationTo( target );
            }
        };
    }

    public T getEdgeSource( TRelation relation ) {
        return (T)relation.getSource();
    }

    public T getEdgeTarget( TRelation relation ) {
        return (T)relation.getTarget();
    }

    public Set<T> vertexSet() {
        return relations.keySet();
    }

}
