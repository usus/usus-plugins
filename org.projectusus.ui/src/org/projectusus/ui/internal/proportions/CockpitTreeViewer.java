// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.ui.internal.viewer.UsusTreeViewer;

class CockpitTreeViewer extends UsusTreeViewer<CodeProportion> {

    private final static int STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;

    CockpitTreeViewer( Composite parent ) {
        super( parent, STYLE, CockpitColumnDesc.values() );
        setUseHashlookup( true );

        setLabelProvider( new CockpitLP() );
        setContentProvider( new CockpitCP() );
    }
}
