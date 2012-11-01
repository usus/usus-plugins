package org.projectusus.core.filerelations.model;

import java.util.Collection;
import java.util.Set;

import org.projectusus.c4j.UsusContractBase;

public class RelationsContract<K> extends UsusContractBase<Relations<K>> {

    private String targetString() {
        return " Meth-Target: " + m_target;
    }

    public RelationsContract( Relations<K> target ) {
        super( target );
    }

    public void classInvariant() {
        for( Relation<K> relation : m_target.getAllDirectRelations() ) {
            assertThat( m_target.getDirectRelationsFrom( relation.getSource() ).contains( relation ), "Each relation is in the relations-from set" + targetString() );
            assertThat( m_target.getDirectRelationsTo( relation.getTarget() ).contains( relation ), "Each relation is in the relations-to set" + targetString() );
        }
    }

    public void pre_Relations() {
        // no pre-condition identified yet
    }

    public void post_Relations() {
        // no post-condition identified yet
    }

    public void pre_getDirectRelationsFrom( K sourceFile ) {
        assertThat( sourceFile != null, "sourceFile_not_null" + targetString() );
    }

    public void post_getDirectRelationsFrom( K sourceFile ) {
        Set<Relation<K>> returnValue = (Set<Relation<K>>)getReturnValue();
        for( Relation<K> relation : returnValue ) {
            assertThat( relation.getSource().equals( sourceFile ), "Method argument is source of all returned relations" + targetString() );
        }
        if( !m_target.containsKey( sourceFile ) ) {
            assertThat( returnValue.isEmpty(), "No direct relations when method argument is not in key list" + targetString() );
        }
    }

    public void pre_getDirectRelationsTo( K targetFile ) {
        assertThat( targetFile != null, "targetFile_not_null" );
    }

    public void post_getDirectRelationsTo( K targetFile ) {
        Set<Relation<K>> returnValue = (Set<Relation<K>>)getReturnValue();
        for( Relation<K> relation : returnValue ) {
            assertThat( relation.getTarget().equals( targetFile ), "Method argument is target of all returned relations" + targetString() );
        }
        if( !m_target.containsKey( targetFile ) ) {
            assertThat( returnValue.isEmpty(), "No direct relations when method argument is not in key list" + targetString() );
        }
    }

    public void pre_removeDirectRelationsFrom( K sourceFile ) {
        assertThat( sourceFile != null, "sourceFile_not_null" + targetString() );
    }

    public void post_removeDirectRelationsFrom( K sourceFile ) {
        assertThat( m_target.getDirectRelationsFrom( sourceFile ).isEmpty(), "No direct relations from method argument are available" + targetString() );
        for( Relation<K> relation : m_target.getAllDirectRelations() ) {
            assertThat( !relation.getSource().equals( sourceFile ), "No direct relations have method argument as source" + targetString() );
        }
    }

    public void pre_getAllDirectRelations() {
        // no pre-condition identified yet
    }

    public void post_getAllDirectRelations() {
        Collection<Relation<K>> returnValue = (Collection<Relation<K>>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public void pre_remove( K sourceKey, K targetKey ) {
        assertThat( sourceKey != null, "sourceKey_not_null" + targetString() );
        assertThat( targetKey != null, "targetKey_not_null" + targetString() );
    }

    public void post_remove( K sourceKey, K targetKey ) {
        for( Relation<K> relation : m_target.getDirectRelationsFrom( sourceKey ) ) {
            assertThat( !relation.getTarget().equals( targetKey ), "Relations from removed source do not contain relations to removed target" + targetString() );
        }
        for( Relation<K> relation : m_target.getDirectRelationsTo( targetKey ) ) {
            assertThat( !relation.getSource().equals( sourceKey ), "Relations to removed target do not contain relations from removed source" + targetString() );
        }
    }

    public void pre_containsKey( K key ) {
        assertThat( key != null, "key_not_null" + targetString() );
    }

    public void post_containsKey( K key ) {
        boolean returnValue = ((Boolean)getReturnValue()).booleanValue();
        assertThat( returnValue == m_target.keySet().contains( key ), "Relations contains key iff keyset contains key" + targetString() );
    }

    public void pre_keySet() {
        // TODO no pre-condition identified yet
    }

    public void post_keySet() {
        Set<K> returnValue = (Set<K>)getReturnValue();
        // TODO no post-condition identified yet
    }

}
