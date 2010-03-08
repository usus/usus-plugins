package org.projectusus.core.filerelations.model;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Classname {

    private String name;

    public Classname( String name ) {
        this.name = name;
    }

    @Override
    public boolean equals( Object obj ) {
        return (obj instanceof Classname) && name.equals( ((Classname)obj).name );
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append( name );
        return builder.toHashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
