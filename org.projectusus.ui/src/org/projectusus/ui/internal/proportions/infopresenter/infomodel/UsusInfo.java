// Copyright (c) 2009 by the projectusus.org contributors
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
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.proportions.rawdata.IClassRawData;
import org.projectusus.core.internal.proportions.rawdata.IMethodRawData;

class UsusInfo implements IUsusInfo {

    private final IMethod method;
    private final IMethodRawData methodRawData;
    private final IClassRawData classRawData;
    private final BugList bugs;

    UsusInfo( IMethod method, IMethodRawData methodRawData, IClassRawData classRawData, BugList bugs ) {
        this.method = method;
        this.methodRawData = methodRawData;
        this.classRawData = classRawData;
        this.bugs = bugs;
    }

    public String[] getCodeProportionInfos() {
        List<String> result = new ArrayList<String>();
        UsusModelElementFormatter formatter = new UsusModelElementFormatter();
        result.add( formatter.format( CC, methodRawData.getCCValue() ) );
        result.add( formatter.format( ML, methodRawData.getMLValue() ) );
        result.add( formatter.format( KG, classRawData.getNumberOfMethods() ) );
        return result.toArray( new String[0] );
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
