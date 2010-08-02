package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.internal.model.CrossPackageClassRelations;
import org.projectusus.core.filerelations.model.ClassDescriptor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class CrossPackageClassRepresenter extends AbstractClassRepresenter {

    private static CrossPackageClassRelations relations;

    public static Set<GraphNode> transformToRepresenterSet( Set<ClassDescriptor> classes, final CrossPackageClassRelations rel ) {
        relations = rel;
        Function<ClassDescriptor, CrossPackageClassRepresenter> function = new Function<ClassDescriptor, CrossPackageClassRepresenter>() {
            public CrossPackageClassRepresenter apply( ClassDescriptor descriptor ) {
                return new CrossPackageClassRepresenter( descriptor );
            }
        };
        return new HashSet<GraphNode>( Collections2.transform( classes, function ) );
    }

    private CrossPackageClassRepresenter( ClassDescriptor clazz ) {
        super( clazz );
    }

    public Set<? extends GraphNode> getChildren() {
        return transformToRepresenterSet( relations.getDirectChildrenFrom( clazz ), relations );
    }

    public String getNodeName() {
        return clazz.qualifiedClassName();
    }

    public boolean isVisibleFor( int limit ) {
        if( limit > 0 ) {
            return relations.getCrossPackageClassCycles().containsClass( clazz );
        }
        return true;
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof CrossPackageClassRepresenter && super.equals( obj );
    }

    public int getFilterValue() {
        return 0;
    }
}
