package org.projectusus.ui.dependencygraph.handlers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.projectusus.core.basis.SinglePackageHotspot;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.ui.dependencygraph.ClassGraphView;
import org.projectusus.ui.dependencygraph.filters.ClassDescriptorNodeFilter;
import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.internal.hotspots.commands.AbstractOpenHotspotHandler;

public class OpenHotspotInClassGraph extends AbstractOpenHotspotHandler<SinglePackageHotspot> {

    @Override
    protected void open( List<DisplayHotspot<SinglePackageHotspot>> hotspots ) throws ExecutionException {
        ClassDescriptorNodeFilter filter = new ClassDescriptorNodeFilter();
        Set<ClassDescriptor> classes = new LinkedHashSet<ClassDescriptor>();
        for( DisplayHotspot<SinglePackageHotspot> hotspot : hotspots ) {
            classes.addAll( hotspot.getCurrentOrOldHotspot().getRelevantClasses() );
        }
        filter.setClasses( classes );
        new DependencyGraphViewFilterer( getActivePage() ).applyFilterToView( ClassGraphView.VIEW_ID, filter );
    }

    private IWorkbenchPage getActivePage() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
