// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import org.projectusus.core.internal.UsusXmlWriter;

public class BugsWriter extends UsusXmlWriter implements BugXmlNames {

    private final BugList bugs;

    public BugsWriter( BugList bugs ) {
        this.bugs = bugs;
    }

    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append( PREAMBLE );
        sb.append( openTag( ROOT ) );
        for( Bug bug : bugs ) {
            toXml( bug, sb );
        }
        sb.append( closeTag( ROOT ) );
        return sb.toString();
    }

    private void toXml( Bug bug, StringBuilder sb ) {
        sb.append( INDENT + tagStart( BUG_TAG ) );
        sb.append( att( TITLE_TAG, bug.getTitle() ) );
        sb.append( att( PACKAGE_NAME_TAG, bug.getPackageName() ) );
        sb.append( att( CLASS_NAME_TAG, bug.getClassName() ) );
        sb.append( att( METHOD_NAME_TAG, bug.getMethodName() ) );
        sb.append( att( CREATION_TIME_TAG, bug.getCreationTime() ) );
        sb.append( att( REPORT_TIME_TAG, bug.getReportTime() ) );
        BugMetrics bugMetrics = bug.getBugMetrics();
        sb.append( att( CYCLOMATIC_COMPLEXITY_TAG, bugMetrics.getCyclomaticComplexity() ) );
        sb.append( att( METHOD_LENGTH_TAG, bugMetrics.getMethodLength() ) );
        sb.append( att( NUMBER_OF_METHODS_TAG, bugMetrics.getNumberOfMethods() ) );
        sb.append( tagEndOpen() );
        sb.append( closeTag( BUG_TAG ) );
    }

}
