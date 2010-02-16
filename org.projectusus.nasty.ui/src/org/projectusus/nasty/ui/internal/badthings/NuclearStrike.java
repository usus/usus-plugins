// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static org.projectusus.nasty.ui.internal.util.TracingOption.NUCLEAR_STRIKE;

import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.nasty.ui.internal.IBadThingThatHappensPeriodically;

public class NuclearStrike implements IBadThingThatHappensPeriodically {

    public void doIt( IProgressMonitor monitor ) {
        NUCLEAR_STRIKE.trace( "Analyzing" );
        WSAnalysisResult analyzed = new WSAnalysis().compute();
        NUCLEAR_STRIKE.trace( "Result: " + analyzed );
        if( analyzed.canNuke() ) {
            runNuclearStrikeJob( analyzed );
        }
    }

    private void runNuclearStrikeJob( WSAnalysisResult analyzed ) {
        try {
            NuclearStrikeJob job = new NuclearStrikeJob( analyzed );
            job.schedule();
            job.join();
        } catch( InterruptedException irex ) {
            NUCLEAR_STRIKE.trace( irex );
        }
    }
}
