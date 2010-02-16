// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static org.eclipse.core.runtime.jobs.Job.getJobManager;
import static org.projectusus.nasty.ui.internal.util.TracingOption.NUCLEAR_STRIKE;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

public class DisarmNuclearStrike extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        Job[] jobs = getJobManager().find( NuclearStrikeJob.FAMILY );
        if( jobs.length > 0 && jobs[0].getThread() != null ) {
            jobs[0].getThread().interrupt();
            NUCLEAR_STRIKE.trace( "Disarmed" );
        }
        return null; // must return null by IHandler contract
    }
}
