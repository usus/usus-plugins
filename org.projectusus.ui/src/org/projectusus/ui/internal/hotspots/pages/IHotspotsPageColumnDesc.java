// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.ui.internal.viewer.IColumnDesc;

public interface IHotspotsPageColumnDesc extends IColumnDesc<IHotspot> {

    String getLabel( IHotspot element );

}
