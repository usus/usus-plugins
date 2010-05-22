// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugreport.core.texts;

import org.eclipse.osgi.util.NLS;

public final class BugReportTexts extends NLS {

    public static String AverageBugMetrics_allBugs;
    public static String AverageMetrics_overall;

    public static String SaveBugsJob_title;

    private static final String BUNDLE_NAME = BugReportTexts.class.getPackage().getName() + ".bugreporttexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, BugReportTexts.class );
    }
}
