package org.projectusus.ui.internal.proportions;

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

    public String getColumnText( Object element, int columnIndex ) {
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
}
