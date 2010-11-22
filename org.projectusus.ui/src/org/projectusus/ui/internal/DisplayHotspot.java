package org.projectusus.ui.internal;

import static org.projectusus.ui.colors.UsusUIImages.getSharedImages;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.Hotspot;

public abstract class DisplayHotspot<T extends Hotspot> implements Comparable<DisplayHotspot<?>> {

    private final T historyHotspot;
    private final T currentHotspot;

    public DisplayHotspot( T historyHotspot, T currentHotspot ) {
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

    /**
     * result may be null!
     */
    public T getHotspot() {
        return currentHotspot;
    }

    /**
     * result may be null!
     * 
     * testing backdoor
     */
    T getOldHotspot() {
        return historyHotspot;
    }

    public Image getTrendImage() {
        return getSharedImages().getTrendImage( getTrend() );
    }

    public T getCurrentOrOldHotspot() {
        return currentHotspot == null ? historyHotspot : currentHotspot;
    }

    public int getTrend() {
        int currentMetricsValue = currentHotspot == null ? 0 : getMetricsValue();
        int oldMetricsValue = historyHotspot == null ? 0 : historyHotspot.getMetricsValue();
        return -(currentMetricsValue - oldMetricsValue);
    }

    @Override
    public String toString() {
        return getName() + "-" + getMetricsValue() + "[" + super.toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public int compareTo( DisplayHotspot<?> otherHotspot ) {
        int trend1 = this.getTrend();
        int trend2 = otherHotspot.getTrend();
        if( trend1 != trend2 ) {
            return trend1 - trend2;
        }
        return otherHotspot.getMetricsValue() - this.getMetricsValue();
    }

    public String getPath() {
        return getCurrentOrOldHotspot().getPath();
    }

    public abstract IFile getFile();
}
