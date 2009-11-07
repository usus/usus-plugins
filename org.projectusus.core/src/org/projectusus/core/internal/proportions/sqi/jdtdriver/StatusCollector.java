// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi.jdtdriver;

import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;
import static org.projectusus.core.internal.util.CoreTexts.jdtDriver_errors;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

public class StatusCollector {

    private final Set<IStatus> errors = new HashSet<IStatus>();

    public void add( Throwable thr ) {
        if( thr instanceof CoreException ) {
            errors.add( ((CoreException)thr).getStatus() );
        } else {
            addNewStatusFrom( thr );
        }
    }

    public void finish() throws CoreException {
        if( !errors.isEmpty() ) {
            throw new CoreException( createMultiStatus() );
        }
    }

    // internal
    // ////////

    private void addNewStatusFrom( Throwable thr ) {
        String msg = thr.getMessage() == null ? "[No details.]" : thr.getMessage(); //$NON-NLS-1$
        errors.add( new Status( IStatus.ERROR, getPluginId(), msg, thr ) );
    }

    private IStatus createMultiStatus() {
        MultiStatus result = new MultiStatus( getPluginId(), ERROR, jdtDriver_errors, null );
        for( IStatus error : errors ) {
            result.add( error );
        }
        return result;
    }
}
