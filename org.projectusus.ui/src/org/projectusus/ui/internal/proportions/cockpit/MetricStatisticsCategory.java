package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_CODE_PROPORTIONS;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public class MetricStatisticsCategory implements AnalysisDisplayCategory {

    private final List<AnalysisDisplayEntry> entries;

    public MetricStatisticsCategory( List<AnalysisDisplayEntry> entries ) {
        this.entries = entries;
    }

    public AnalysisDisplayEntry[] getChildren() {
        return entries.toArray( new AnalysisDisplayEntry[entries.size()] );
    }

    public String getLabel() {
        return "Code proportions";
    }

    public Image getImage() {
        return getSharedImages().getImage( OBJ_CODE_PROPORTIONS );
    }

}
