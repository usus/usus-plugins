package org.projectusus.core.filerelations.model;

import static com.google.common.collect.Sets.union;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.sourceforge.c4j.ContractReference;

@ContractReference( contractClassName = "RelationsContract" )
public class Relations<K> {

    protected final Multimap<K, Relation<K>> incomingRelations;
    protected final Multimap<K, Relation<K>> outgoingRelations;

    public Relations() {
        incomingRelations = ArrayListMultimap.create();
        outgoingRelations = ArrayListMultimap.create();
    }

    public Set<Relation<K>> getDirectRelationsFrom( K sourceFile ) {
        return getAsSet( outgoingRelations.get( sourceFile ) );
    }

    public Set<Relation<K>> getDirectRelationsTo( K targetFile ) {
        return getAsSet( incomingRelations.get( targetFile ) );
    }

    public void removeDirectRelationsFrom( K sourceFile ) {
        outgoingRelations.removeAll( sourceFile );
    }

    public Collection<Relation<K>> getAllDirectRelations() {
        return Collections.unmodifiableSet( getAsSet( outgoingRelations.values() ) );
    }

    public void add( K sourceKey, K targetKey ) {
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

    protected Collection<Relation<K>> getAllOutgoingRelationsFrom( K key ) {
        return outgoingRelations.get( key );
    }

    protected Collection<Relation<K>> getAllIncomingRelationsTo( K key ) {
        return incomingRelations.get( key );
    }

    private HashSet<Relation<K>> getAsSet( Collection<Relation<K>> collection ) {
        return Sets.newHashSet( collection );
    }
}
