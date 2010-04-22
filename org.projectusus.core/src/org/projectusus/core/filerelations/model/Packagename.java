package org.projectusus.core.filerelations.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Packagename {

    private String name;
    private Set<ClassDescriptor> classes = new HashSet<ClassDescriptor>();

    Packagename( String name ) {
        this.name = name;
    }

    public void addClass( ClassDescriptor clazz ) {
        classes.add( clazz );
    }

    public void removeClass( ClassDescriptor clazz ) {
        classes.remove( clazz );
    }

    public int numberOfClasses() {
        return classes.size();
    }

    @Override
    public boolean equals( Object obj ) {
        return (obj instanceof Packagename) && name.equals( ((Packagename)obj).name );
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append( name ).toHashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean containsClass( ClassDescriptor clazz ) {
        return classes.contains( clazz );
    }
}
