package org.projectusus.core.filerelations.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.jdt.core.IJavaElement;

public class Packagename {

    private static Map<String, Packagename> allPackages = new HashMap<String, Packagename>();
    private static final int NUMBER_OF_COLORS = 16;
    public static final String COLOR_PREFIX = "PACKAGE"; //$NON-NLS-1$
    private static int colorIndex = 0;

    private String name;
    private String color;
    private Set<ClassDescriptor> classesInPackage = new HashSet<ClassDescriptor>();
    private final IJavaElement javaElement;

    public static void clear() {
        allPackages = new HashMap<String, Packagename>();
        colorIndex = 0;
    }

    public static Packagename of( String name, IJavaElement javaElement ) {
        if( allPackages.containsKey( name ) ) {
            return allPackages.get( name );
        }
        Packagename newPackage = new Packagename( name, javaElement );
        allPackages.put( name, newPackage );
        return newPackage;
    }

    public static Set<Packagename> getAll() {
        return new HashSet<Packagename>( allPackages.values() );
    }

    private Packagename( String name, IJavaElement javaElement ) {
        this.name = name;
        this.javaElement = javaElement;
        this.color = COLOR_PREFIX + Integer.toString( colorIndex );
        colorIndex = (colorIndex + 1) % NUMBER_OF_COLORS;
    }

    public void addClass( ClassDescriptor clazz ) {
        classesInPackage.add( clazz );
    }

    public void removeClass( ClassDescriptor clazz ) {
        classesInPackage.remove( clazz );
        if( classesInPackage.isEmpty() ) {
            allPackages.remove( name );
        }
    }

    public boolean containsClass( ClassDescriptor clazz ) {
        return classesInPackage.contains( clazz );
    }

    public int numberOfClasses() {
        return classesInPackage.size();
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

    public IJavaElement getJavaElement() {
        return javaElement;
    }

    public String getColor() {
        return color;
    }

    public Relation<Packagename> getRelationTo( Packagename target ) {
        return new Relation<Packagename>( this, target );
    }
}
