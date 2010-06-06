// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;

class UnavailableUsusInfo implements IUsusInfo {

    private static final String[] MESSAGE = new String[] { "No information available at this time." };
    private final IJavaElement element;

    UnavailableUsusInfo( IJavaElement element ) {
        this.element = element;
    }

    public String[] getCodeProportionInfos() {
        return MESSAGE;
    }

    public String[] getWarningInfos() {
        return MESSAGE;
    }

    public String formatTitle() {
        if( element instanceof IMethod ) {
            return new MethodFormatter( (IMethod)element ).formatHeadInfo();
        }
        return "n.n.";
    }
}
