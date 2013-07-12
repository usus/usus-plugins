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
        return getSharedImages().getTrendImage( oldMetricsValue(), currentMetricsValue() );
    }

    public T getCurrentOrOldHotspot() {
        return currentHotspot == null ? historyHotspot : currentHotspot;
    }

    public int getTrend() {
        return currentMetricsValue() - oldMetricsValue();
    }

    public int oldMetricsValue() {
        return historyHotspot == null ? 0 : historyHotspot.getMetricsValue();
    }

    public int currentMetricsValue() {
        return currentHotspot == null ? 0 : getMetricsValue();
    }

    @Override
    public String toString() {
        return getName() + "-" + getMetricsValue() + "[" + super.toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public int compareTo( DisplayHotspot<?> otherHotspot ) {
        int thisValue = this.getMetricsValue();
        int otherValue = otherHotspot.getMetricsValue();
        if( thisValue != otherValue ) {
            return otherValue - thisValue;
        }
        return otherHotspot.getTrend() - this.getTrend();
    }

    public String getPath() {
        return getCurrentOrOldHotspot().getPath();
    }

    public abstract IFile getFile();
}
