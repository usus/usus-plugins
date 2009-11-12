// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.util;

import org.eclipse.osgi.util.NLS;

public final class UITexts extends NLS {

    public static String checkpointsHistoryChart_title;
    public static String checkpointsHistoryChart_x;
    public static String checkpointsHistoryChart_y;

    public static String cockpitColumnDesc_cases;
    public static String cockpitColumnDesc_indicator;
    public static String cockpitColumnDesc_sqi;
    public static String cockpitColumnDesc_violations;

    public static String cockpitLP_codeProportions;
    public static String cockpitLP_testCoverage;
    public static String cockpitLP_warnings;

    public static String columnDesc_covered;
    public static String columnDesc_project;

    public static String defaultHotspotsPage_info;

    public static String hotspotsColumn_class;
    public static String hotspotsColumn_cyclomaticComplexity;
    public static String hotspotsColumn_length;
    public static String hotspotsColumn_line;
    public static String hotspotsColumn_method;
    public static String hotspotsColumn_path;
    public static String hotspotsColumn_size;
    public static String hotspotsColumn_warnings;

    public static String openCheckpoints_noLog_title;
    public static String openCheckpoints_noLog_msg;

    public static String openHotspots_label;

    public static String projectSelector_title;
    public static String projectSelector_msg;

    public static String projectsettings_select_message;
    public static String projectsettings_select_title;

    public static String showCoverageView_label;
    public static String showProblemsView_label;

    private static final String BUNDLE_NAME = UITexts.class.getPackage().getName() + ".uitexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, UITexts.class );
    }
}
