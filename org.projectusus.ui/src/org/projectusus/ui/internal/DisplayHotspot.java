package org.projectusus.ui.internal;

import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_LEVELDOWN;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_LEVELUP;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.Hotspot;

public class DisplayHotspot {

    private final Hotspot historyHotspot;
    private final Hotspot currentHotspot;

    public DisplayHotspot( Hotspot historyHotspot, Hotspot currentHotspot ) {
        super();
        this.historyHotspot = historyHotspot;
        this.currentHotspot = currentHotspot;
    }

    public String getName() {
        return getCurrentOrOldHotspot().getName();
    }

    public int getMetricsValue() {
        if( currentHotspot == null ) {
            return 0;
        }
        return getCurrentOrOldHotspot().getMetricsValue();
    }

    public IFile getFile() {
        return getCurrentOrOldHotspot().getFile();
    }

    /**
     * result may be null!
     */
    public Hotspot getHotspot() {
        return currentHotspot;
    }

    public Image getTrendImage() {
        if( currentHotspot != null && historyHotspot != null ) {
            double diff = getMetricsValue() - historyHotspot.getMetricsValue();
            if( diff == 0.0 ) {
                return null;
            }
            String imageName = diff < 0 ? OBJ_LEVELUP : OBJ_LEVELDOWN;
            return getSharedImages().getImage( imageName );
        }
        if( currentHotspot == null ) {
            return getSharedImages().getImage( OBJ_LEVELUP );
        }
        return getSharedImages().getImage( OBJ_LEVELDOWN );
    }

    public Hotspot getCurrentOrOldHotspot() {
        return currentHotspot != null ? currentHotspot : historyHotspot;
    }
}
