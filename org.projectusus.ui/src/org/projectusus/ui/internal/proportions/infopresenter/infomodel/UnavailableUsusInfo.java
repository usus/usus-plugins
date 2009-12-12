// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.jdt.core.IMethod;

class UnavailableUsusInfo implements IUsusInfo {

    private static final String[] MESSAGE = new String[] { "No information available at this time." };
    private final IMethod method;

    UnavailableUsusInfo( IMethod method ) {
        this.method = method;
    }

    public String[] getBugInfos() {
        return MESSAGE;
    }

    public String[] getCodeProportionInfos() {
        return MESSAGE;
    }

    public String[] getTestCoverageInfos() {
        return MESSAGE;
    }

    public String[] getWarningInfos() {
        return MESSAGE;
    }

    public String formatMethod() {
        return new MethodFormatter( method ).formatHeadInfo();
    }
}
