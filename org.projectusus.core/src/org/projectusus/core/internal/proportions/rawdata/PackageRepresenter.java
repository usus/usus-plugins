package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.internal.model.PackageRelations;
import org.projectusus.core.filerelations.model.Packagename;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class PackageRepresenter implements GraphNode {

    private final Packagename packagename;
    private static PackageRelations relations;

    public static Set<GraphNode> transformToRepresenterSet( Set<Packagename> classes, final PackageRelations rel ) {
        relations = rel;
        Function<Packagename, PackageRepresenter> function = new Function<Packagename, PackageRepresenter>() {
            public PackageRepresenter apply( Packagename descriptor ) {
                return new PackageRepresenter( descriptor );
            }
        };
        return new HashSet<GraphNode>( Collections2.transform( classes, function ) );
    }

    private PackageRepresenter( Packagename pkg ) {
        this.packagename = pkg;
    }

    public Set<? extends GraphNode> getChildren() {
        return transformToRepresenterSet( relations.getDirectPackageRelationsFrom( packagename ), relations );
    }

    public String getEdgeEndLabel() {
        return ""; //$NON-NLS-1$
    }

    public String getEdgeMiddleLabel() {
        return ""; //$NON-NLS-1$
    }

    public String getEdgeStartLabel() {
        return ""; //$NON-NLS-1$
    }

    public String getNodeName() {
        return packagename.toString();
    }

    public boolean isVisibleFor( int limit ) {
        if( limit > 0 ) {
            return relations.getPackageCycles().containsPackage( packagename );
        }
        return true;
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof PackageRepresenter && packagename.equals( ((PackageRepresenter)obj).packagename );
    }

    @Override
    public int hashCode() {
        return packagename.hashCode();
    }

    public int getFilterValue() {
        return 0;
    }

    public boolean isPackage() {
        return true;
    }

}
