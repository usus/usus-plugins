package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_class;
import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_path;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.yellowcount.IMetricCWHotspot;
import org.projectusus.ui.internal.util.UITexts;

public enum CWColumnDesc implements IHotspotsPageColumnDesc {
    WARNINGS( UITexts.hotspotsColumn_warnings, 10, false ) {
        public String getLabel( IHotspot element ) {
            IMetricCWHotspot hotspot = (IMetricCWHotspot)element;
            return String.valueOf( hotspot.getWarningCount() );
        }
    },
    CLASS( hotspotsColumn_class, 25, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricCWHotspot)element).getFileName();
        }
    },
    PATH( hotspotsColumn_path, 20, false ) {
        public String getLabel( IHotspot element ) {
            return element.getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;

    private CWColumnDesc( String headLabel, int weight, boolean hasImage ) {
        this.headLabel = headLabel;
        this.weight = weight;
        this.hasImage = hasImage;
    }

    public int getWeight() {
        return weight;
    }

    public String getHeadLabel() {
        return headLabel;
    }

    public boolean hasImage() {
        return hasImage;
    }
}
