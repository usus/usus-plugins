package org.projectusus.core.filerelations.model;

import org.eclipse.jdt.core.IJavaElement;

public class Classname {

    private final String name;
    private final IJavaElement javaElement;

    public Classname( String name, IJavaElement javaElement ) {
        this.name = name;
        this.javaElement = javaElement;
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

    public IJavaElement getJavaElement() {
        return javaElement;
    }
}
