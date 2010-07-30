package org.projectusus.ui.internal;

import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.CodeProportion;

public class AnalysisDisplayEntry {

    private CodeProportion history;
    private CodeProportion current;

    private List<DisplayHotspot> displayHotspots;

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

    public double getLevel() {
        return current.getLevel();
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

    public List<DisplayHotspot> getHotspots() {
        if( displayHotspots == null ) {
            createHotspots();
        }
        return displayHotspots;
    }

    private void createHotspots() {
        if( hasHotspots() ) {
            displayHotspots = new DisplayHotspotCreator( history.getHotspots(), current.getHotspots() ).hotspots();
        } else {
            displayHotspots = new ArrayList<DisplayHotspot>();
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
     * package private for testing
     */
    int getTrend() {
        if( hasHotspots() ) {
            return -(getHotspots().size() - history.getHotspots().size());
        }
        return (int)-(getLevel() - history.getLevel());
    }

    /**
     * package private for testing
     */
    int getAdvancedTrend() {
        if( hasHotspots() ) {
            int result = 0;
            for( DisplayHotspot hotspot : getHotspots() ) {
                if( hotspot.getTrend() < 0 ) {
                    return -1;
                }
                result += hotspot.getTrend();
            }
            return result;
        }
        return getTrend();
    }

}
