// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.MetricStatisticsCategory;
import org.projectusus.ui.viewer.UsusTreeViewer;

class CockpitTreeViewer extends UsusTreeViewer<AnalysisDisplayEntry> {

    CockpitTreeViewer( Composite parent ) {
        super( parent, CockpitColumnDesc.values() );
        setUseHashlookup( true );
        ColumnViewerToolTipSupport.enableFor( this );
        setLabelProvider( new CockpitLP() );
        setContentProvider( new CockpitCP() );
    }

    void selectInTree( Object object ) {
        if( object instanceof AnalysisDisplayEntry ) {
            selectCodeProportionInTree( ((AnalysisDisplayEntry)object) );
        }
    }

    private void selectCodeProportionInTree( AnalysisDisplayEntry displayEntry ) {
        for( Object element : getExpandedElements() ) {
            if( element instanceof MetricStatisticsCategory ) {
                for( AnalysisDisplayEntry entry : ((MetricStatisticsCategory)element).getChildren() ) {
                    if( entry.isSameKindAs( displayEntry ) ) {
                        setSelection( createTreeSelection( new Object[] { element, entry } ) );
                    }
                }
            }
        }
    }

    private TreeSelection createTreeSelection( Object... elements ) {
        return new TreeSelection( new TreePath( elements ) );
    }

}
