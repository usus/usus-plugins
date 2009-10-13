// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.util;

import org.eclipse.osgi.util.NLS;

public final class CoreTexts extends NLS {

    public static String codeProportionsComputerJob_computing;
    public static String codeProportionsComputerJob_name;

    private static final String BUNDLE_NAME = CoreTexts.class.getPackage().getName() + ".coretexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, CoreTexts.class );
    }
}
