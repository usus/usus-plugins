// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.cockpit;

import static org.eclipse.swt.SWT.COLOR_GRAY;
import static org.projectusus.core.basis.CodeProportionKind.TA;
import static org.projectusus.ui.internal.proportions.cockpit.CockpitColumnDesc.INDICATOR;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.ui.internal.proportions.UsusModelLabelProvider;

public class CockpitLP extends UsusModelLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( Object element, int columnIndex ) {
        Image result = null;
        if( CockpitColumnDesc.values()[columnIndex].hasImage() ) {
            result = getColumnImageFor( element );
        }
        return result;
    }

    public String getColumnText( Object element, int columnIndex ) {
        String result = null;
        if( element instanceof CodeProportion ) {
            result = getColumnTextFor( (CodeProportion)element, columnIndex );
        } else if( CockpitColumnDesc.values()[columnIndex] == INDICATOR ) {
            result = getNodeTextFor( element );
        }
        return result;
    }

    @Override
    public Color getForeground( Object element ) {
        Color result = null;
        if( isStaleBecauseOfMissingTestCoverage( element ) ) {
            result = Display.getDefault().getSystemColor( COLOR_GRAY );
        }
        return result;
    }

    // internal methods
    // ////////////////

    private boolean isStaleBecauseOfMissingTestCoverage( Object element ) {
        boolean result = false;
        if( element instanceof CodeProportion ) {
            CodeProportion codeProportion = (CodeProportion)element;
            result = isTestCoverageMetric( codeProportion ) && false;
        }
        return result;
    }

    private boolean isTestCoverageMetric( CodeProportion codeProportion ) {
        return codeProportion.getMetric() == TA;
    }

    private String getColumnTextFor( CodeProportion element, int columnIndex ) {
        return CockpitColumnDesc.values()[columnIndex].getLabel( element );
    }
}
