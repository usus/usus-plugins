// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import static java.lang.String.valueOf;
import static org.projectusus.ui.internal.proportions.CockpitColumnDesc.INDICATOR;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_CODE_PROPORTIONS;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_INFO;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_TEST_COVERAGE;
import static org.projectusus.ui.internal.util.ISharedUsusImages.OBJ_WARNINGS;
import static org.projectusus.ui.internal.util.UITexts.cockpitLP_codeProportions;
import static org.projectusus.ui.internal.util.UITexts.cockpitLP_testCoverage;
import static org.projectusus.ui.internal.util.UITexts.cockpitLP_warnings;
import static org.projectusus.ui.internal.util.UsusUIImages.getSharedImages;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.model.ICodeProportions;
import org.projectusus.core.internal.proportions.model.ITestCoverage;
import org.projectusus.core.internal.proportions.model.IWarnings;

public class CockpitLP extends LabelProvider implements ITableLabelProvider {

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
        String result = null;
        switch( CockpitColumnDesc.values()[columnIndex] ) {
        case INDICATOR:
            result = element.getMetric().getLabel();
            break;
        case VIOLATIONS:
            result = valueOf( element.getViolations() );
            break;
        case CASES:
            result = valueOf( element.getBasis() );
            break;
        }
        return result;
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
