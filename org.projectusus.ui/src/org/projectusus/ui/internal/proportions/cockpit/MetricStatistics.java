package org.projectusus.ui.internal.proportions.cockpit;

import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_CODE_PROPORTIONS;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.projectusus.core.basis.CodeProportion;

public class MetricStatistics implements CockpitCategory {

    private final List<CodeProportion> entries;

    public MetricStatistics( List<CodeProportion> entries ) {
        this.entries = entries;
    }

    public CodeProportion[] getChildren() {
        return entries.toArray( new CodeProportion[entries.size()] );
    }

    public String getLabel() {
        return "Code proportions";
    }

    public Image getImage() {
        return getSharedImages().getImage( OBJ_CODE_PROPORTIONS );
    }

}
