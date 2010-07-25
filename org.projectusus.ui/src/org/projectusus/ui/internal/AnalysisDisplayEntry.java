package org.projectusus.ui.internal;

import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_LEVELDOWN;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_LEVELUP;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.Hotspot;

public class AnalysisDisplayEntry implements Cloneable {

    private final String label;
    private final double level;
    private final int violations;
    private final String basis;
    private final Double levelOfSnapshot;
    private final List<Hotspot> hotspots;
    private final boolean hasHotspots;

    public AnalysisDisplayEntry( String label, double level, int violations, String basis, boolean hasHotspots, List<Hotspot> hotspots, Double levelOfSnapshot ) {
        super();
        this.label = label;
        this.level = level;
        this.violations = violations;
        this.basis = basis;
        this.hasHotspots = hasHotspots;
        this.hotspots = hotspots;
        this.levelOfSnapshot = levelOfSnapshot;
    }

    public String getLabel() {
        return label;
    }

    public double getLevel() {
        return level;
    }

    public int getViolations() {
        return violations;
    }

    public String getBasis() {
        return basis;
    }

    public boolean isSameKindAs( AnalysisDisplayEntry displayEntry ) {
        return isSameKindAs( displayEntry.getLabel() );
    }

    public boolean isSameKindAs( String otherLabel ) {
        return getLabel().equals( otherLabel );
    }

    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    public boolean hasHotspots() {
        return hasHotspots;
    }

    public Image getTrendImage() {
        if( levelOfSnapshot == null ) {
            return null;
        }
        double diff = getLevel() - levelOfSnapshot.doubleValue();
        if( diff == 0.0 ) {
            return null;
        }
        String imageName = diff < 0 ? OBJ_LEVELDOWN : OBJ_LEVELUP;
        return getSharedImages().getImage( imageName );
    }

}
