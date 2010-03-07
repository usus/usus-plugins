package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.model.ClassDescriptor;

public class ClassRepresenter {

    private final ClassRawData clazz;

    public ClassRepresenter( ClassRawData clazz ) {
        this.clazz = clazz;
    }

    public static Set<ClassRepresenter> transformToRepresenterSet( Set<ClassRawData> classes ) {
        Set<ClassRepresenter> representers = new HashSet<ClassRepresenter>();
        for( ClassRawData clazz : classes ) {
            representers.add( clazz.getRepresenter() );
        }
        return representers;
    }

    public Set<ClassDescriptor> getChildren() {
        return clazz.getChildren();
    }

    public String getClassName() {
        return clazz.getClassName();
    }

    public int getNumberOfAllChildren() {
        return 0; // TODO
    }

    public int getNumberOfChildren() {
        return 0; // TODO
    }

    public int getNumberOfParents() {
        return 0; // TODO
    }

}
