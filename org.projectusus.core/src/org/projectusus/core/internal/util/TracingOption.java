// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.util;

import static org.eclipse.core.runtime.Platform.getDebugOption;

import org.projectusus.core.UsusCorePlugin;

public enum TracingOption {

    SQI( "[SQI]" ), //$NON-NLS-1$
    RESOURCE_CHANGES( "[ResCh]" ); //$NON-NLS-1$ 

    private final String tracePrefix;
    private final boolean tracing;

    private TracingOption( String tracePrefix ) {
        this.tracePrefix = tracePrefix;
        this.tracing = isTracing();
    }

    public void trace( String content ) {
        if( tracing ) {
            System.out.println( tracePrefix + " " + content ); //$NON-NLS-1$
        }
    }

    public void trace( Throwable thr ) {
        if( tracing ) {
            trace( thr.getMessage() );
            thr.printStackTrace();
        }
    }

    private boolean isTracing() {
        String option = UsusCorePlugin.PLUGIN_ID + "/" + toString(); //$NON-NLS-1$
        String value = getDebugOption( option );
        return value != null && value.equalsIgnoreCase( "true" ); //$NON-NLS-1$
    }
}
