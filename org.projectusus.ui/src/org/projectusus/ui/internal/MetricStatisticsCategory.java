package org.projectusus.ui.internal;

import static org.projectusus.ui.colors.ISharedUsusImages.OBJ_CODE_PROPORTIONS;
import static org.projectusus.ui.colors.UsusUIImages.getSharedImages;

import org.eclipse.swt.graphics.Image;

public class MetricStatisticsCategory implements IDisplayCategory {

    private final AnalysisDisplayEntry[] entries;

    public MetricStatisticsCategory( AnalysisDisplayEntry[] entries ) {
        this.entries = entries;
    }

    public AnalysisDisplayEntry[] getChildren() {
        return entries;
    }

    public String getLabel() {
        return "Code proportions"; //$NON-NLS-1$
    }

    public Image getImage() {
        return getSharedImages().getImage( OBJ_CODE_PROPORTIONS );
    }

}
