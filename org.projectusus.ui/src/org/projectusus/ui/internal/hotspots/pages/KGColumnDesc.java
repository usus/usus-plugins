package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_class;
import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_path;
import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_size;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricKGHotspot;

public enum KGColumnDesc implements IHotspotsPageColumnDesc {
    SIZE( hotspotsColumn_size, 10, false ) {
        public String getLabel( IHotspot element ) {
            IMetricKGHotspot hotspot = (IMetricKGHotspot)element;
            return String.valueOf( hotspot.getClassSize() );
        }
    },
    CLASS( hotspotsColumn_class, 25, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricKGHotspot)element).getClassName();
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

    private KGColumnDesc( String headLabel, int weight, boolean hasImage ) {
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
