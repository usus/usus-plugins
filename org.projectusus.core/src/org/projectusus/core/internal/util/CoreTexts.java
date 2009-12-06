// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.util;

import org.eclipse.osgi.util.NLS;

public final class CoreTexts extends NLS {

    public static String AverageBugMetrics_allBugs;
    public static String AverageMetrics_overall;

    public static String codeProportionsComputerJob_computing;
    public static String codeProportionsComputerJob_name;

    public static String codeProportionUnit_CLASS_label;
    public static String codeProportionUnit_FILE_label;
    public static String codeProportionUnit_LINE_label;
    public static String codeProportionUnit_METHOD_label;
    public static String codeProportionUnit_PACKAGE_label;

    public static String isisMetrics_acd;
    public static String isisMetrics_cc;
    public static String isisMetrics_cw;
    public static String isisMetrics_kg;
    public static String isisMetrics_ml;
    public static String isisMetrics_pc;
    public static String isisMetrics_ta;

    public static String jdtDriver_computing;
    public static String jdtDriver_errors;

    public static String projectSettings_defaultName;
    public static String projectSettings_settingsName;

    public static String saveCheckpointsJob_name;

    public static String SaveBugsJob_title;

    public static String SettingsAccess_load_settings;
    public static String SettingsAccess_save_settings;

    public static String testCoverage;

    public static String ususModelStatus_ok;
    public static String ususModelStatus_stale;

    public static String wiseCrack_cool;
    public static String wiseCrack_shame;

    public static String yellowCountResult_msg;

    private static final String BUNDLE_NAME = CoreTexts.class.getPackage().getName() + ".coretexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, CoreTexts.class );
    }
}
