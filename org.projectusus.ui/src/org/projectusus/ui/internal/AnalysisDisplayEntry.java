package org.projectusus.ui.internal;

import static org.projectusus.ui.colors.UsusUIImages.getSharedImages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.Histogram;
import org.projectusus.core.basis.LocationType;

public class AnalysisDisplayEntry {

    private CodeProportion history;
    private CodeProportion current;

    private List<DisplayHotspot<?>> displayHotspots;

    public AnalysisDisplayEntry( CodeProportion codeProportion ) {
        super();
        history = codeProportion;
        setCodeProportion( codeProportion );
    }

    public void setCodeProportion( CodeProportion codeProportion ) {
        current = codeProportion;
        displayHotspots = null;
    }

    public String getLabel() {
        return current.getMetricLabel();
    }

    public String getDescription() {
        return current.getMetricDescription();
    }

    public double getAverage() {
        return current.getAverage();
    }

    public int getViolations() {
        return current.getViolations();
    }

    public String getBasis() {
        return current.getBasis().toString();
    }

    public boolean isSameKindAs( AnalysisDisplayEntry displayEntry ) {
        return isSameKindAs( displayEntry.getLabel() );
    }

    public boolean isSameKindAs( String otherLabel ) {
        return getLabel().equals( otherLabel );
    }

    public List<DisplayHotspot<?>> getHotspots() {
        if( displayHotspots == null ) {
            createHotspots();
        }
        return displayHotspots;
    }

    private void createHotspots() {
        if( hasHotspots() ) {
            displayHotspots = new DisplayHotspotCreator( history.getHotspots(), current.getHotspots() ).hotspots();
        } else {
            displayHotspots = new ArrayList<DisplayHotspot<?>>();
        }
    }

    public boolean hasHotspots() {
        return current.hasHotspots();
    }

    public Image getTrendImage() {
        return getSharedImages().getTrendImage( getAdvancedTrend() );
    }

    public boolean matches( CodeProportion codeProportion ) {
        return isSameKindAs( codeProportion.getMetricLabel() );
    }

    public void createSnapshot() {
        history = current;
        displayHotspots = null;
    }

    /**
     * Indicates how many hotspot points got added or removed.
     * 
     * package private for testing
     */
    int getTrend() {
        if( hasHotspots() ) {
            return getHotspots().size() - history.getHotspots().size();
        }
        return (int)(getAverage() - history.getAverage());
    }

    /**
     * Indicates how many hotspot points got added or removed.
     * 
     * package private for testing
     */
    int getAdvancedTrend() {
        if( !hasHotspots() ) {
            return getTrend();
        }

        int result = 0;
        for( DisplayHotspot<?> hotspot : getHotspots() ) {
            result += hotspot.getTrend();
        }
        return result;
    }

    public String getToolTipText() {
        return current.getToolTipText();
    }

    public Histogram getHistogram() {
        return current.getHistogram();
    }

    public LocationType getLocationType() {
        return current.getLocationType();
    }
}
