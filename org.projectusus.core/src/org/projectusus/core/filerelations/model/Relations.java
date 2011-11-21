package org.projectusus.core.filerelations.model;

import net.sourceforge.c4j.*;

import static com.google.common.collect.Sets.union;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

@ContractReference(contractClassName = "RelationsContract")
public class Relations<K> {

    protected final SetMultimap<K, Relation<K>> incomingRelations;
    protected final SetMultimap<K, Relation<K>> outgoingRelations;

    public Relations() {
        incomingRelations = HashMultimap.create();
        outgoingRelations = HashMultimap.create();
    }

    public Set<Relation<K>> getDirectRelationsFrom( K sourceFile ) {
        return outgoingRelations.get( sourceFile );
    }

    public Set<Relation<K>> getDirectRelationsTo( K targetFile ) {
        return incomingRelations.get( targetFile );
    }

    public void removeDirectRelationsFrom( K sourceFile ) {
        outgoingRelations.removeAll( sourceFile );
    }

    public Collection<Relation<K>> getAllDirectRelations() {
        return Collections.unmodifiableCollection( outgoingRelations.values() );
    }

    protected void add( K sourceKey, K targetKey ) {
        Relation<K> relation = new Relation<K>( sourceKey, targetKey );
        outgoingRelations.put( sourceKey, relation );
        incomingRelations.put( targetKey, relation );
    }

    public void remove( K sourceKey, K targetKey ) {
        Relation<K> relation = new Relation<K>( sourceKey, targetKey );
        outgoingRelations.remove( sourceKey, relation );
        incomingRelations.remove( targetKey, relation );
    }

    public boolean containsKey( K key ) {
        return outgoingRelations.containsKey( key ) || incomingRelations.containsKey( key );
    }

    public Set<K> keySet() {
        return union( outgoingRelations.keySet(), incomingRelations.keySet() );
    }

    protected Set<Relation<K>> getOutgoingRelationsFrom( K key ) {
        return outgoingRelations.get( key );
    }

    protected Set<Relation<K>> getIncomingRelationsTo( K key ) {
        return incomingRelations.get( key );
    }
}
