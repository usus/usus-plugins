// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.projectusus.core.internal.proportions.model.IHotspot;

public interface IHotspotsPageColumnDesc {

    String getHeadLabel();

    String getLabel( IHotspot element );

    boolean hasImage();

    // column weight (percentage of overall width in the table that this column takes
    int getWeight();
}
