package org.projectusus.ui.internal.hotspots.commands;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.ui.internal.DisplayHotspot;

public abstract class AbstractOpenHotspotHandler<T extends Hotspot> extends AbstractHandler {

    public static final String COMMAND_ID = "org.projectusus.ui.commands.OpenHotspot"; //$NON-NLS-1$

    @SuppressWarnings( "unchecked" )
    public Object execute( ExecutionEvent event ) throws ExecutionException {
        List<DisplayHotspot<T>> hotspots = (List<DisplayHotspot<T>>)event.getApplicationContext();
        if( hotspots != null ) {
            open( event, hotspots );
        }
        return null;
    }

    protected void open( ExecutionEvent event, List<DisplayHotspot<T>> hotspots ) throws ExecutionException {
        for( DisplayHotspot<T> hotspot : hotspots ) {
            open( event, hotspot );
        }
    }

    abstract protected void open( ExecutionEvent event, DisplayHotspot<T> hotspot ) throws ExecutionException;

}
