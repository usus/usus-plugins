package org.projectusus.ui.internal;

import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_LEVELDOWN;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_LEVELUP;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.CodeProportion;

public class AnalysisDisplayEntry {

    private CodeProportion history;
    private CodeProportion current;

    public AnalysisDisplayEntry( CodeProportion codeProportion ) {
        super();
        history = codeProportion;
        setCodeProportion( codeProportion );
    }

    public void setCodeProportion( CodeProportion codeProportion ) {
        current = codeProportion;
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
        if( hasHotspots() ) {
            return createDisplayHotspots();
        }
        return new ArrayList<DisplayHotspot>();
    }

    private List<DisplayHotspot> createDisplayHotspots() {
        return new DisplayHotspotCreator( history.getHotspots(), current.getHotspots() ).hotspots();
    }

    public boolean hasHotspots() {
        return current.hasHotspots();
    }

    public Image getTrendImage() {
        String imageName = "";
        if( hasHotspots() ) {
            int diff = getHotspots().size() - history.getHotspots().size();
            if( diff == 0 ) {
                return null;
            }
            imageName = diff < 0 ? OBJ_LEVELUP : OBJ_LEVELDOWN;
        } else {
            double diff = getLevel() - history.getLevel();
            if( diff == 0.0 ) {
                return null;
            }
            imageName = diff < 0 ? OBJ_LEVELDOWN : OBJ_LEVELUP;
        }
        return getSharedImages().getImage( imageName );
    }

    public boolean matches( CodeProportion codeProportion ) {
        return isSameKindAs( codeProportion.getMetricLabel() );
    }

    public void createSnapshot() {
        history = current;
    }
}
