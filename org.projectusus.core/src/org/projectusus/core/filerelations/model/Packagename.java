package org.projectusus.core.filerelations.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Packagename {

    private String name;
    private Set<ClassDescriptor> classes = new HashSet<ClassDescriptor>();

    private static Map<String, Packagename> packages = new HashMap<String, Packagename>();

    public static Packagename of( String name ) {
        if( packages.containsKey( name ) ) {
            return packages.get( name );
        }
        Packagename newPackage = new Packagename( name );
        packages.put( name, newPackage );
        return newPackage;
    }

    public static Set<Packagename> getAll() {
        return new HashSet<Packagename>( packages.values() );
    }

    private Packagename( String name ) {
        this.name = name;
    }

    public void addClass( ClassDescriptor clazz ) {
        classes.add( clazz );
    }

    public void removeClass( ClassDescriptor clazz ) {
        classes.remove( clazz );
        if( classes.isEmpty() ) {
            packages.remove( name );
        }
    }

    public boolean containsClass( ClassDescriptor clazz ) {
        return classes.contains( clazz );
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

    public PackageRelation getRelationTo( Packagename target ) {
        return new PackageRelation( this, target );
    }
}
