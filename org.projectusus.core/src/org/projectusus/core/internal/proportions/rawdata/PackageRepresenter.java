package org.projectusus.core.internal.proportions.rawdata;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.Packagename;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class PackageRepresenter implements GraphNode {

    private final Packagename packagename;
    private static FileRelationMetrics relations;

    public static Set<PackageRepresenter> transformToRepresenterSet( Set<Packagename> classes, final FileRelationMetrics rel ) {
        Function<Packagename, PackageRepresenter> function = new Function<Packagename, PackageRepresenter>() {
            public PackageRepresenter apply( Packagename descriptor ) {
                return new PackageRepresenter( descriptor, rel );
            }
        };
        return new HashSet<PackageRepresenter>( Collections2.transform( classes, function ) );
    }

    public PackageRepresenter( Packagename pkg, FileRelationMetrics relations ) {
        this.packagename = pkg;
        initRelations( relations );
    }

    private void initRelations( FileRelationMetrics relations ) {
        if( PackageRepresenter.relations == null ) {
            PackageRepresenter.relations = relations;
        }
    }

    public Set<? extends GraphNode> getChildren() {
        return transformToRepresenterSet( relations.getChildren( packagename ), relations );
    }

    public String getEdgeEndLabel() {
        return "";
    }

    public String getEdgeMiddleLabel() {
        return "";
    }

    public String getEdgeStartLabel() {
        return "";
    }

    public String getNodeName() {
        return packagename.toString();
    }

    public boolean isVisibleFor( int limit ) {
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

}
