package org.projectusus.core.filerelations.internal.model;

import static com.google.common.collect.Sets.union;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.projectusus.core.filerelations.model.Relation;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class Relations<K, R extends Relation<?>> {

    protected final SetMultimap<K, R> incomingRelations;
    protected final SetMultimap<K, R> outgoingRelations;

    public Relations() {
        incomingRelations = HashMultimap.create();
        outgoingRelations = HashMultimap.create();
    }

    public Set<R> getDirectRelationsFrom( K sourceFile ) {
        return outgoingRelations.get( sourceFile );
    }

    public Set<R> getDirectRelationsTo( K targetFile ) {
        return incomingRelations.get( targetFile );
    }

    public void removeDirectRelationsFrom( K sourceFile ) {
        outgoingRelations.removeAll( sourceFile );
    }

    public Collection<R> getAllDirectRelations() {
        return Collections.unmodifiableCollection( outgoingRelations.values() );
    }

    public void add( R relation, K sourceKey, K targetKey ) {
        outgoingRelations.put( sourceKey, relation );
        incomingRelations.put( targetKey, relation );
    }

    public boolean containsKey( K key ) {
        return outgoingRelations.containsKey( key ) || incomingRelations.containsKey( key );
    }

    public Set<K> keySet() {
        return union( outgoingRelations.keySet(), incomingRelations.keySet() );
    }

    protected Set<R> getOutgoingRelationsFrom( K key ) {
        return outgoingRelations.get( key );
    }
}
