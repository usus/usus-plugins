package org.projectusus.ui.dependencygraph.filters;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.rawdata.PackageRepresenter;

import com.google.common.base.Joiner;

public class PackagenameNodeFilter extends NodeFilter {

    private Collection<Packagename> packages;

    public void setPackages( Collection<Packagename> packages ) {
        this.packages = packages;
    }

    public void resetPackages() {
        setPackages( null );
    }

    public static PackagenameNodeFilter from( ISelection selection ) {
        PackagenameNodeFilter filter = new PackagenameNodeFilter();
        if( selection instanceof IStructuredSelection ) {
            filter.setPackages( collectPackagenames( (IStructuredSelection)selection ) );
        }
        return filter;
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
        return packages == null || packages.isEmpty();
    }

    @Override
    public boolean select( GraphNode node ) {
        return packages == null || node.isPackageOneOf( packages );
    }

    @Override
    public String getDescription() {
        return "Only classes in one of the following packages: " + Joiner.on( ", " ).join( packages );
    }
}
