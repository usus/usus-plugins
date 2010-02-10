package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

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

    public Set<ClassRepresenter> getChildren() {
        return transformToRepresenterSet( clazz.getChildren() );
    }

    public String getClassName() {
        return clazz.getClassName();
    }

    public Set<ClassRepresenter> getAllChildren() {
        return transformToRepresenterSet( clazz.getAllChildren() );
    }

    public Set<ClassRepresenter> getParents() {
        return transformToRepresenterSet( clazz.getParents() );
    }

}
