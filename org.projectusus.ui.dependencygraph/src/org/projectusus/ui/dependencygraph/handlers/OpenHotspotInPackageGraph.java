package org.projectusus.ui.dependencygraph.handlers;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.basis.PackageHotspot;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.ui.dependencygraph.PackageGraphView;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;
import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.internal.hotspots.commands.AbstractOpenHotspotHandler;

public class OpenHotspotInPackageGraph extends AbstractOpenHotspotHandler<PackageHotspot> {

    @Override
    protected void open( List<DisplayHotspot<PackageHotspot>> hotspots ) throws ExecutionException {
        PackagenameNodeFilter filter = new PackagenameNodeFilter();
        Set<Packagename> packages = new LinkedHashSet<Packagename>();
        for( DisplayHotspot<PackageHotspot> hotspot : hotspots ) {
            packages.addAll( hotspot.getCurrentOrOldHotspot().getElementsInCycle() );
        }
        filter.setPackages( packages );
        filter.setEdges( Collections.<EntityConnectionData> emptySet() );
        new DependencyGraphViewFilterer( getActivePage() ).applyFilterToView( PackageGraphView.VIEW_ID, filter );
    }

    private IWorkbenchPage getActivePage() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
