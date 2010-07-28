package org.projectusus.ui.internal;

import org.eclipse.swt.graphics.Image;

public interface IDisplayCategory {

    AnalysisDisplayEntry[] getChildren();

    String getLabel();

    Image getImage();

}
