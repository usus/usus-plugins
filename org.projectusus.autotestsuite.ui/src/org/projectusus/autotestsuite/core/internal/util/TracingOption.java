// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal.util;

import static org.eclipse.core.runtime.Platform.getDebugOption;

import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

public enum TracingOption {

    TCA( "[TCA]" );

    private final String tracePrefix;
    private final boolean tracing;

    private TracingOption( String tracePrefix ) {
        this.tracePrefix = tracePrefix;
        this.tracing = isTracing();
    }

    public void trace( String content ) {
        if( tracing ) {
            System.out.println( tracePrefix + " " + content );
        }
    }

    public void trace( Throwable thr ) {
        if( tracing ) {
            trace( thr.getMessage() );
            thr.printStackTrace();
        }
    }

    private boolean isTracing() {
        String option = AutoTestSuitePlugin.getPluginId() + "/" + toString();
        String value = getDebugOption( option );
        return value != null && value.equalsIgnoreCase( "true" );
    }
}
