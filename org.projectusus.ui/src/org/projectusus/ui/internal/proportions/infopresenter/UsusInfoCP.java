// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import org.projectusus.ui.internal.proportions.cockpit.CockpitCP;
import org.projectusus.ui.internal.proportions.cockpit.AnalysisDisplayCategory;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;

class UsusInfoCP extends CockpitCP {

    private final IUsusInfo ususInfo;

    UsusInfoCP( IUsusInfo ususInfo ) {
        this.ususInfo = ususInfo;
    }

    @Override
    public Object[] getChildren( Object parentElement ) {
        Object[] result = new Object[0];
        if( parentElement instanceof AnalysisDisplayCategory ) {
            result = ususInfo.getCodeProportionInfos();
        }
        return result;
    }
}
