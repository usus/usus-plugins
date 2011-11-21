package org.projectusus.core.filerelations.model;

import net.sourceforge.c4j.ContractBase;

public class RelationContract<T> extends ContractBase<Relation<T>> {

    private T source;
    private T target;

    public RelationContract( Relation<T> target ) {
        super( target );
    }

    public void classInvariant() {
        // no class invariant identified yet
    }

    public void pre_Relation( T source, T target ) {
        assert source != null : "source_not_null";
        assert target != null : "target_not_null";
        assert !source.equals( target ) : "source and target are different";
        this.source = source;
        this.target = target;
    }

    public void post_Relation( T source, T target ) {
        // no post-condition identified yet
    }

    public void pre_getSource() {
        // no pre-condition identified yet
    }

    public void post_getSource() {
        T returnValue = (T)getReturnValue();
        assert returnValue.equals( source ) : "getSource provides the first constructor argument";
    }

    public void pre_getTarget() {
        // no pre-condition identified yet
    }

    public void post_getTarget() {
        T returnValue = (T)getReturnValue();
        assert returnValue.equals( target ) : "getTarget provides the second constructor argument";
    }

}
