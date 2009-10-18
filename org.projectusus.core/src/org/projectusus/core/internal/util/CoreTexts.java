// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.util;

import org.eclipse.osgi.util.NLS;

public final class CoreTexts extends NLS {

    public static String codeProportionsComputerJob_computing;
    public static String codeProportionsComputerJob_name;

    public static String isisMetrics_acd;
    public static String isisMetrics_cc;
    public static String isisMetrics_cw;
    public static String isisMetrics_kg;
    public static String isisMetrics_ml;
    public static String isisMetrics_pc;
    public static String isisMetrics_ta;

    public static String wiseCrack_cool;
    public static String wiseCrack_shame;

    public static String yellowCountResult_msg;

    private static final String BUNDLE_NAME = CoreTexts.class.getPackage().getName() + ".coretexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, CoreTexts.class );
    }
}
