package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.swt.graphics.Image;

public interface AnalysisDisplayCategory {

    AnalysisDisplayEntry[] getChildren();

    String getLabel();

    Image getImage();

}
