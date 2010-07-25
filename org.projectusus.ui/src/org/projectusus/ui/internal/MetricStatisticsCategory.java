package org.projectusus.ui.internal;

import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_CODE_PROPORTIONS;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import org.eclipse.swt.graphics.Image;

public class MetricStatisticsCategory implements AnalysisDisplayCategory {

    private final AnalysisDisplayEntry[] entries;

    public MetricStatisticsCategory( AnalysisDisplayEntry[] entries ) {
        this.entries = entries;
    }

    public AnalysisDisplayEntry[] getChildren() {
        return entries;
    }

    public String getLabel() {
        return "Code proportions";
    }

    public Image getImage() {
        return getSharedImages().getImage( OBJ_CODE_PROPORTIONS );
    }

}
