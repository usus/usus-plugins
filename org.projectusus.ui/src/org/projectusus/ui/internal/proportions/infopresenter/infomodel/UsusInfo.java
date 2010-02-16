// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.bugreport.BugList;

class UsusInfo implements IUsusInfo {

    private final IMethod method;
    private final BugList bugs;

    UsusInfo( IMethod method, BugList bugs ) {
        this.method = method;
        this.bugs = bugs;
    }

    public String[] getCodeProportionInfos() {
        List<String> result = new ArrayList<String>();
        UsusModelElementFormatter formatter = new UsusModelElementFormatter();

        try {
            result.add( formatter.format( CC, UsusCorePlugin.getUsusModel().getCCValue( method ) ) );
            result.add( formatter.format( ML, UsusCorePlugin.getUsusModel().getMLValue( method ) ) );
            result.add( formatter.format( KG, UsusCorePlugin.getUsusModel().getNumberOfMethods( method.getDeclaringType() ) ) );
            return result.toArray( new String[0] );
        } catch( JavaModelException jmox ) {
            return new String[] { "Error in calculating metrics values." };
        }
    }

    public String[] getBugInfos() {
        return new String[] { new UsusModelElementFormatter().format( bugs ) };
    }

    public String[] getTestCoverageInfos() {
        return new String[] { "Not yet available" };
    }

    public String[] getWarningInfos() {
        return new String[] { "Not yet available" };
    }

    public String formatMethod() {
        return new MethodFormatter( method ).formatHeadInfo();
    }
}
