package org.projectusus.ui.dependencygraph.filters;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.rawdata.PackageRepresenter;

import com.google.common.base.Joiner;

public class PackagenameNodeFilter extends NodeFilter {

    private Collection<Packagename> packages;
    private Collection<EntityConnectionData> edges;

    private void setPackages( Collection<Packagename> packages ) {
        this.packages = packages;
    }

    private void setEdges( Collection<EntityConnectionData> collectedEdges ) {
        edges = collectedEdges;
    }

    public void resetPackages() {
        setPackages( null );
        setEdges( null );
    }

    public static PackagenameNodeFilter from( ISelection selection ) {
        PackagenameNodeFilter filter = new PackagenameNodeFilter();
        if( selection instanceof IStructuredSelection ) {
            filter.setPackages( collectPackagenames( (IStructuredSelection)selection ) );
            filter.setEdges( collectEdges( (IStructuredSelection)selection ) );
        }
        return filter;
    }

    private static Collection<EntityConnectionData> collectEdges( IStructuredSelection selection ) {
        final Collection<EntityConnectionData> edges = new LinkedList<EntityConnectionData>();
        for( Object item : selection.toList() ) {
            addEdges( edges, item );
        }
        return edges;
    }

    private static void addEdges( Collection<EntityConnectionData> edges, Object item ) {
        if( item instanceof EntityConnectionData ) {
            edges.add( (EntityConnectionData)item );
        }
    }

    private static Collection<Packagename> collectPackagenames( IStructuredSelection selection ) {
        final Collection<Packagename> packages = new LinkedList<Packagename>();
        for( Object item : selection.toList() ) {
            add( packages, item );
        }
        return packages;
    }

    private static void add( final Collection<Packagename> packages, Object item ) {
        if( item instanceof PackageRepresenter ) {
            add( packages, (PackageRepresenter)item );
        } else if( item instanceof IPackageFragment ) {
            add( packages, (IPackageFragment)item );
        }
    }

    private static void add( final Collection<Packagename> packages, IPackageFragment fragment ) {
        packages.add( Packagename.of( fragment.getElementName(), fragment ) );
    }

    private static void add( final Collection<Packagename> packages, PackageRepresenter representer ) {
        packages.add( representer.getPackagename() );
    }

    public boolean isEmpty() {
        return (packages == null || packages.isEmpty()) && (edges == null || edges.isEmpty());
    }

    @Override
    public boolean select( GraphNode node ) {
        return (packages == null || node.isPackageOneOf( packages )) || (edges == null || isPartOfEdges( node ));
    }

    private boolean isPartOfEdges( GraphNode node ) {
        for( EntityConnectionData edge : edges ) {
            if( node.isAtEitherEndOf( ((PackageRepresenter)edge.source).getPackagename(), ((PackageRepresenter)edge.dest).getPackagename() ) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Only classes in one of the following packages: " + Joiner.on( ", " ).join( packages );
    }
}
