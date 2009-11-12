// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import static org.projectusus.ui.internal.util.UITexts.showCoverageView_label;

public class ShowCoverageView extends ShowViewById {

    public ShowCoverageView() {
        super( showCoverageView_label, "com.mountainminds.eclemma.ui.CoverageView" ); //$NON-NLS-1$
    }
}
