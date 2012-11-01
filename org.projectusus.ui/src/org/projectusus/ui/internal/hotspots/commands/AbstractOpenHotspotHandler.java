package org.projectusus.ui.internal.hotspots.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.jfeet.selection.ElementsFrom;
import org.projectusus.ui.internal.DisplayHotspot;

public abstract class AbstractOpenHotspotHandler<T extends Hotspot> extends AbstractHandler {

    public static final String COMMAND_ID = "org.projectusus.ui.commands.OpenHotspot"; //$NON-NLS-1$

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        ISelection selection = getCurrentSelection( event );
        List<DisplayHotspot<T>> hotspots = collectHotspots( selection );
        open( hotspots );
        return null;
    }

    private ISelection getCurrentSelection( ExecutionEvent event ) throws ExecutionException {
        return HandlerUtil.getCurrentSelectionChecked( event );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    private ArrayList<DisplayHotspot<T>> collectHotspots( ISelection selection ) {
        List hotspots = new ElementsFrom( selection ).as( DisplayHotspot.class );
        return new ArrayList<DisplayHotspot<T>>( hotspots );
    }

    abstract protected void open( List<DisplayHotspot<T>> hotspots ) throws ExecutionException;

}
