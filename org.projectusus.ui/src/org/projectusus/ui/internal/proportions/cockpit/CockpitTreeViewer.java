// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.swt.widgets.Composite;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.ui.internal.viewer.UsusTreeViewer;

class CockpitTreeViewer extends UsusTreeViewer<CodeProportion> {

    CockpitTreeViewer( Composite parent ) {
        super( parent, CockpitColumnDesc.values() );
        setUseHashlookup( true );

        setLabelProvider( new CockpitLP() );
        setContentProvider( new CockpitCP() );
    }
}
