package org.projectusus.core.filerelations;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Packagename {

    private String name;

    public Packagename( String name ) {
        this.name = name;
    }

    @Override
    public boolean equals( Object obj ) {
        return (obj instanceof Packagename) && name.equals( ((Packagename)obj).name );
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append( name );
        return builder.toHashCode();
    }

}
