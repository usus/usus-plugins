package org.projectusus.core.filerelations.model;

import net.sourceforge.c4j.ContractBase;

public class RelationContract<T> extends ContractBase<Relation<T>> {

    private T source;
    private T target;

    private String targetString() {
        return " Meth-Target: " + m_target;
    }

    public RelationContract( Relation<T> target ) {
        super( target );
    }

    public void classInvariant() {
        // no class invariant identified yet
    }

    public void pre_Relation( T source, T target ) {
        String arguments = " source: " + source + " target: " + target;
        assert source != null : "source_not_null:" + arguments;
        assert target != null : "target_not_null" + arguments;
        assert !source.equals( target ) : "source and target are different" + arguments;
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
        assert returnValue.equals( source ) : "getSource provides the first constructor argument" + targetString();
    }

    public void pre_getTarget() {
        // no pre-condition identified yet
    }

    public void post_getTarget() {
        T returnValue = (T)getReturnValue();
        assert returnValue.equals( target ) : "getTarget provides the second constructor argument" + targetString();
    }

}
