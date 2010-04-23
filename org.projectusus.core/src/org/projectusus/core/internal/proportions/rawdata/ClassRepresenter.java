package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.ClassDescriptor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class ClassRepresenter implements GraphNode {

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

    public static Set<ClassRepresenter> transformToRepresenterSet( Set<ClassDescriptor> classes, final FileRelationMetrics rel ) {
        Function<ClassDescriptor, ClassRepresenter> function = new Function<ClassDescriptor, ClassRepresenter>() {
            public ClassRepresenter apply( ClassDescriptor descriptor ) {
                return new ClassRepresenter( descriptor, rel );
            }
        };
        return new HashSet<ClassRepresenter>( Collections2.transform( classes, function ) );
    }

    public Set<ClassRepresenter> getChildren() {
        return transformToRepresenterSet( relations.getChildren( clazz ), relations );
    }

    public String getNodeName() {
        return clazz.getClassname().toString();
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof ClassRepresenter && clazz.equals( ((ClassRepresenter)obj).clazz );
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    public String getEdgeStartLabel() {
        return ""; // + relations.getTransitiveParentCount( clazz ); //$NON-NLS-1$
    }

    public String getEdgeMiddleLabel() {
        return ""; //$NON-NLS-1$
    }

    public String getEdgeEndLabel() {
        return "" + relations.getCCD( clazz ); //$NON-NLS-1$
    }

    public boolean isVisibleFor( int limit ) {
        return relations.getBottleneckCount( this.clazz ) >= limit;
    }
}
