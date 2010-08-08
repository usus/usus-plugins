package org.projectusus.ui.dependencygraph.filters;

import static com.google.common.collect.Collections2.transform;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.proportions.rawdata.PackageRepresenter;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

public class PackagenameNodeFilter extends NodeFilter {

    private static final Joiner commaJoiner = Joiner.on( ", " );
    private static final Function<EntityConnectionData, String> toArrowSeparatedStrings = new Function<EntityConnectionData, String>() {
        public String apply( EntityConnectionData edge ) {
            return ((PackageRepresenter)edge.source).getPackagename() + " \u2192 " + ((PackageRepresenter)edge.dest).getPackagename();
        }
    };

    private Collection<Packagename> packages;
    private Collection<EntityConnectionData> edges;

    public void setPackages( Collection<Packagename> packages ) {
        this.packages = packages;
    }

    public void setEdges( Collection<EntityConnectionData> collectedEdges ) {
        edges = collectedEdges;
    }

    public void resetPackages() {
        setPackages( null );
        setEdges( null );
    }

    public static PackagenameNodeFilter from( ISelection selection ) {
        if( selection instanceof IStructuredSelection ) {
            return from( (IStructuredSelection)selection );
        }
        return new PackagenameNodeFilter();
    }

    public static PackagenameNodeFilter from( IStructuredSelection selection ) {
        PackagenameNodeFilter filter = new PackagenameNodeFilter();
        filter.setPackages( collectPackages( selection ) );
        filter.setEdges( collectEdges( selection ) );
        return filter;
    }

    private static Collection<EntityConnectionData> collectEdges( IStructuredSelection selection ) {
        final Collection<EntityConnectionData> edges = new LinkedList<EntityConnectionData>();
        for( Object item : selection.toList() ) {
            addEdge( edges, item );
        }
        return edges;
    }

    private static void addEdge( Collection<EntityConnectionData> edges, Object item ) {
        if( item instanceof EntityConnectionData ) {
            edges.add( (EntityConnectionData)item );
        }
    }

    private static Collection<Packagename> collectPackages( IStructuredSelection selection ) {
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
        return packagesAreEmpty() && edgesAreEmpty();
    }

    public boolean packagesAreEmpty() {
        return nullOrEmpty( packages );
    }

    public boolean edgesAreEmpty() {
        return nullOrEmpty( edges );
    }

    public boolean nullOrEmpty( Collection<?> collection ) {
        return collection == null || collection.isEmpty();
    }

    @Override
    public boolean select( GraphNode node ) {
        return selectedByPackages( node ) || selectedByEdges( node );
    }

    private boolean selectedByEdges( GraphNode node ) {
        return edges == null || isPartOfEdges( node );
    }

    private boolean selectedByPackages( GraphNode node ) {
        return packages == null || node.isPackageOneOf( packages );
    }

    private boolean isPartOfEdges( GraphNode node ) {
        for( EntityConnectionData edge : edges ) {
            Packagename source = ((PackageRepresenter)edge.source).getPackagename();
            Packagename target = ((PackageRepresenter)edge.dest).getPackagename();
            if( node.isAtEitherEndOf( source, target ) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        Collection<String> parts = new LinkedList<String>();
        describeEdgesTo( parts );
        describePackagesTo( parts );
        return Joiner.on( '\n' ).join( parts );
    }

    private void describeEdgesTo( Collection<String> parts ) {
        if( edgesAreEmpty() ) {
            return;
        }
        parts.add( "Only items that cause one of the following package references: " + commaJoiner.join( transform( edges, toArrowSeparatedStrings ) ) );
    }

    private void describePackagesTo( Collection<String> parts ) {
        if( packagesAreEmpty() ) {
            return;
        }
        parts.add( "Only items in one of the following packages: " + commaJoiner.join( packages ) );
    }
}
