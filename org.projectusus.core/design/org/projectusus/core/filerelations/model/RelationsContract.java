package org.projectusus.core.filerelations.model;

import java.util.Collection;
import java.util.Set;

import net.sourceforge.c4j.ContractBase;

public class RelationsContract<K> extends ContractBase<Relations<K>> {

    public RelationsContract( Relations<K> target ) {
        super( target );
    }

    public void classInvariant() {
        for( Relation<K> relation : m_target.getAllDirectRelations() ) {
            assert m_target.getDirectRelationsFrom( relation.getSource() ).contains( relation ) : "Each relation is in the relations-from set";
            assert m_target.getDirectRelationsTo( relation.getTarget() ).contains( relation ) : "Each relation is in the relations-to set";
        }
    }

    public void pre_Relations() {
        // no pre-condition identified yet
    }

    public void post_Relations() {
        // no post-condition identified yet
    }

    public void pre_getDirectRelationsFrom( K sourceFile ) {
        assert sourceFile != null : "sourceFile_not_null";
    }

    public void post_getDirectRelationsFrom( K sourceFile ) {
        Set<Relation<K>> returnValue = (Set<Relation<K>>)getReturnValue();
        for( Relation<K> relation : returnValue ) {
            assert relation.getSource().equals( sourceFile ) : "Method argument is source of all returned relations";
        }
        if( !m_target.containsKey( sourceFile ) ) {
            assert returnValue.isEmpty() : "No direct relations when method argument is not in key list";
        }
    }

    public void pre_getDirectRelationsTo( K targetFile ) {
        assert targetFile != null : "targetFile_not_null";
    }

    public void post_getDirectRelationsTo( K targetFile ) {
        Set<Relation<K>> returnValue = (Set<Relation<K>>)getReturnValue();
        for( Relation<K> relation : returnValue ) {
            assert relation.getTarget().equals( targetFile ) : "Method argument is target of all returned relations";
        }
        if( !m_target.containsKey( targetFile ) ) {
            assert returnValue.isEmpty() : "No direct relations when method argument is not in key list";
        }
    }

    public void pre_removeDirectRelationsFrom( K sourceFile ) {
        assert sourceFile != null : "sourceFile_not_null";
    }

    public void post_removeDirectRelationsFrom( K sourceFile ) {
        assert m_target.getDirectRelationsFrom( sourceFile ).isEmpty() : "No direct relations from method argument are available";
        for( Relation<K> relation : m_target.getAllDirectRelations() ) {
            assert !relation.getSource().equals( sourceFile ) : "No direct relations have method argument as source";
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
        assert sourceKey != null : "sourceKey_not_null";
        assert targetKey != null : "targetKey_not_null";
    }

    public void post_remove( K sourceKey, K targetKey ) {
        for( Relation<K> relation : m_target.getDirectRelationsFrom( sourceKey ) ) {
            assert !relation.getTarget().equals( targetKey ) : "Relations from removed source do not contain relations to removed target";
        }
        for( Relation<K> relation : m_target.getDirectRelationsTo( targetKey ) ) {
            assert !relation.getSource().equals( sourceKey ) : "Relations to removed target do not contain relations from removed source";
        }
    }

    public void pre_containsKey( K key ) {
        assert key != null : "key_not_null";
    }

    public void post_containsKey( K key ) {
        boolean returnValue = ((Boolean)getReturnValue()).booleanValue();
        assert returnValue == m_target.keySet().contains( key ) : "Relations contains key iff keyset contains key";
    }

    public void pre_keySet() {
        // TODO no pre-condition identified yet
    }

    public void post_keySet() {
        Set<K> returnValue = (Set<K>)getReturnValue();
        // TODO no post-condition identified yet
    }

}
