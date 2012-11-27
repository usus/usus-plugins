package org.projectusus.core.filerelations.model;


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
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
