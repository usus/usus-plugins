// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.ui.internal.viewer.UsusTreeViewer;

class CockpitTreeViewer extends UsusTreeViewer<CodeProportion> {

    CockpitTreeViewer( Composite parent ) {
        super( parent, CockpitColumnDesc.values() );
        setUseHashlookup( true );

        setLabelProvider( new CockpitLP() );
        setContentProvider( new CockpitCP() );
    }

    void selectInTree( Object object ) {
        if( object instanceof CodeProportion ) {
            selectCodeProportionInTree( ((CodeProportion)object) );
        }
    }

    private void selectCodeProportionInTree( CodeProportion codeProportion ) {
        for( Object element : getExpandedElements() ) {
            if( element instanceof IUsusElement ) {
                for( CodeProportion cp : ((IUsusElement)element).getEntries() ) {
                    if( cp.getMetric().equals( codeProportion.getMetric() ) ) {
                        setSelection( createTreeSelection( new Object[] { element, cp } ) );
                    }
                }
            }
        }
    }

    private TreeSelection createTreeSelection( Object... elements ) {
        return new TreeSelection( new TreePath( elements ) );
    }

}
