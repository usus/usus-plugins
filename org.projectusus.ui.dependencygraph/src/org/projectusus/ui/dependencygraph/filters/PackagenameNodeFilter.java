package org.projectusus.ui.dependencygraph.filters;

import java.util.Collection;
import java.util.LinkedList;

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

    public void setPackagesFrom( ISelection selection ) {
        setPackages( collectPackagenames( (IStructuredSelection)selection ) );
    }

    private Collection<Packagename> collectPackagenames( IStructuredSelection selection ) {
        final Collection<Packagename> packages = new LinkedList<Packagename>();
        for( Object item : selection.toList() ) {
            if( item instanceof PackageRepresenter ) {
                packages.add( ((PackageRepresenter)item).getPackagename() );
            }
        }
        return packages;
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
