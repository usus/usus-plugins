// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import org.projectusus.core.internal.proportions.model.ICodeProportions;
import org.projectusus.core.internal.proportions.model.ITestCoverage;
import org.projectusus.core.internal.proportions.model.IWarnings;
import org.projectusus.ui.internal.proportions.cockpit.CockpitCP;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;

class UsusInfoCP extends CockpitCP {

    private final IUsusInfo ususInfo;

    UsusInfoCP( IUsusInfo ususInfo ) {
        this.ususInfo = ususInfo;
    }

    @Override
    public Object[] getChildren( Object parentElement ) {
        Object[] result = new Object[0];
        if( parentElement instanceof ICodeProportions ) {
            result = ususInfo.getCodeProportionInfos();
        } else if( parentElement instanceof ITestCoverage ) {
            result = ususInfo.getTestCoverageInfos();
        } else if( parentElement instanceof IWarnings ) {
            result = ususInfo.getWarningInfos();
        }
        // TODO lf don't have an IUsusElement for bugs
        // result = ususInfo.getBugInfos();
        return result;
    }
}
