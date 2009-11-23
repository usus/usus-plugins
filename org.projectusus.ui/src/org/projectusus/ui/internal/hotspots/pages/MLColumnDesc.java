// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_class;
import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_line;
import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_method;
import static org.projectusus.ui.internal.util.UITexts.hotspotsColumn_path;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.IMetricMLHotspot;
import org.projectusus.ui.internal.util.UITexts;

public enum MLColumnDesc implements IHotspotsPageColumnDesc {

    LENGTH( UITexts.hotspotsColumn_length, 10, false ) {
        public String getLabel( IHotspot element ) {
            IMetricMLHotspot hotspot = (IMetricMLHotspot)element;
            return String.valueOf( hotspot.getMethodLength() );
        }
    },
    METHOD( hotspotsColumn_method, 35, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getMethodName();
        }
    },
    CLASS( hotspotsColumn_class, 25, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getClassName();
        }
    },
    PATH( hotspotsColumn_path, 20, false ) {
        public String getLabel( IHotspot element ) {
            return ((IMetricMLHotspot)element).getFile().getFullPath().removeLastSegments( 1 ).toOSString();
        }
    },
    LINE( hotspotsColumn_line, 10, false ) {
        public String getLabel( IHotspot element ) {
            return String.valueOf( ((IMetricMLHotspot)element).getLineNumber() );
        }
    };

    private final String headLabel;
    private final int weight;
    private final boolean hasImage;

    private MLColumnDesc( String headLabel, int weight, boolean hasImage ) {
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
