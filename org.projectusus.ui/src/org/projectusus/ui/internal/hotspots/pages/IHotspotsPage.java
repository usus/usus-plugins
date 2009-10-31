// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.pages;

import org.eclipse.ui.part.IPageBookViewPage;
import org.projectusus.core.internal.proportions.model.CodeProportion;

public interface IHotspotsPage extends IPageBookViewPage {

    void setInput( CodeProportion element );

    boolean isInitialized();
}
