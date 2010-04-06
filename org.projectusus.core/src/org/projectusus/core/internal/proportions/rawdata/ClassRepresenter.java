package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.ClassDescriptor;

public class ClassRepresenter {

    private final ClassDescriptor clazz;
    private static FileRelationMetrics relations;

    public ClassRepresenter( ClassDescriptor clazz, FileRelationMetrics relations ) {
        this.clazz = clazz;
        initRelations( relations );
    }

    private void initRelations( FileRelationMetrics relations ) {
        if( ClassRepresenter.relations == null ) {
            ClassRepresenter.relations = relations;
        }
    }

    private Set<ClassRepresenter> transformToRepresenterSet( Set<ClassDescriptor> classes ) {
        Set<ClassRepresenter> representers = new HashSet<ClassRepresenter>();
        for( ClassDescriptor clazz : classes ) {
            representers.add( new ClassRepresenter( clazz, relations ) );
        }
        return representers;
    }

    public Set<ClassRepresenter> getChildren() {
        return transformToRepresenterSet( relations.getChildren( clazz ) );
    }

    public String getClassName() {
        return clazz.getClassname().toString();
    }

    public int getNumberOfAllChildren() {
        return relations.getCCD( clazz );
    }

    public int getNumberOfChildren() {
        return 0; // TODO
    }

    public int getNumberOfParents() {
        return 0; // TODO
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof ClassRepresenter && clazz.equals( ((ClassRepresenter)obj).clazz );
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

}
