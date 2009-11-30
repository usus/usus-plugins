// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import static org.eclipse.swt.SWT.COLOR_GRAY;
import static org.projectusus.core.internal.proportions.UsusModel.getUsusModel;
import static org.projectusus.core.internal.proportions.sqi.CodeProportionKind.TA;
import static org.projectusus.ui.internal.proportions.CockpitColumnDesc.INDICATOR;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_CODE_PROPORTIONS;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_INFO;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_TEST_COVERAGE;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_WARNINGS;
import static org.projectusus.ui.internal.util.UITexts.cockpitLP_codeProportions;
import static org.projectusus.ui.internal.util.UITexts.cockpitLP_testCoverage;
import static org.projectusus.ui.internal.util.UITexts.cockpitLP_warnings;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.ICodeProportions;
import org.projectusus.core.internal.proportions.model.ITestCoverage;
import org.projectusus.core.internal.proportions.model.IWarnings;

public class CockpitLP extends LabelProvider implements ITableLabelProvider, IColorProvider {

    public Image getColumnImage( Object element, int columnIndex ) {
        Image result = null;
        if( CockpitColumnDesc.values()[columnIndex].isHasImage() ) {
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

    public Color getBackground( Object element ) {
        return null; // no special treatment
    }

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
            result = isTestCoverageMetric( codeProportion ) && lastStatusIsStale();
        }
        return result;
    }

    private boolean lastStatusIsStale() {
        return getUsusModel().getHistory().getLastStatus().isStale();
    }

    private boolean isTestCoverageMetric( CodeProportion codeProportion ) {
        return codeProportion.getMetric() == TA;
    }

    private String getNodeTextFor( Object element ) {
        String result = super.getText( element );
        if( element instanceof ICodeProportions ) {
            result = cockpitLP_codeProportions;
        } else if( element instanceof ITestCoverage ) {
            result = cockpitLP_testCoverage;
        } else if( element instanceof IWarnings ) {
            result = cockpitLP_warnings;
        }
        return result;
    }

    private String getColumnTextFor( CodeProportion element, int columnIndex ) {
        return CockpitColumnDesc.values()[columnIndex].getLabel( element );
    }

    private Image getColumnImageFor( Object element ) {
        Image result = null;
        if( element instanceof ICodeProportions ) {
            result = getSharedImages().getImage( OBJ_CODE_PROPORTIONS );
        } else if( element instanceof ITestCoverage ) {
            result = getSharedImages().getImage( OBJ_TEST_COVERAGE );
        } else if( element instanceof IWarnings ) {
            result = getSharedImages().getImage( OBJ_WARNINGS );
        } else if( !(element instanceof CodeProportion) ) {
            result = getSharedImages().getImage( OBJ_INFO );
        }
        return result;
    }
}
