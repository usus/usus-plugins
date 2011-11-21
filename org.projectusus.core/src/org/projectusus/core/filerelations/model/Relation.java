package org.projectusus.core.filerelations.model;

import net.sourceforge.c4j.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@ContractReference(contractClassName = "RelationContract")
public class Relation<T> {

    protected final T source;
    protected final T target;

    public Relation( T source, T target ) {
        this.source = source;
        this.target = target;
    }

    public T getSource() {
        return source;
    }

    public T getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append( source );
        builder.append( target );
        return builder.toHashCode();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public boolean equals( Object object ) {
        return object instanceof Relation && equals( (Relation<T>)object );
    }

    private boolean equals( Relation<T> other ) {
        EqualsBuilder builder = new EqualsBuilder();
        builder.append( source, other.source );
        builder.append( target, other.target );
        return builder.isEquals();
    }

    @Override
    public String toString() {
        return source + " -> " + target; //$NON-NLS-1$
    }

}
