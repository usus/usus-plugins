package org.projectusus.ui.internal;

import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.Hotspot;

public class DisplayHotspot implements Comparable<DisplayHotspot> {

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

    /**
     * result may be null!
     * 
     * testing backdoor
     */
    Hotspot getOldHotspot() {
        return historyHotspot;
    }

    public Image getTrendImage() {
        return getSharedImages().getTrendImage( getTrend() );
    }

    public Hotspot getCurrentOrOldHotspot() {
        return currentHotspot != null ? currentHotspot : historyHotspot;
    }

    /**
     * package private for testing
     */
    int getTrend() {
        if( currentHotspot != null && historyHotspot != null ) {
            return -(getMetricsValue() - historyHotspot.getMetricsValue());
        }
        if( currentHotspot == null ) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return getName() + "-" + getMetricsValue() + "[" + super.toString() + "]";
    }

    public int compareTo( DisplayHotspot o ) {
        return getName().compareTo( o.getName() );
    }
}
