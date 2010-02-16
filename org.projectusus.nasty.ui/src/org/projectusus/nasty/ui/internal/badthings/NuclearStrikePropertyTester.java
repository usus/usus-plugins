// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static java.text.MessageFormat.format;
import static org.projectusus.nasty.ui.internal.util.TracingOption.NUCLEAR_STRIKE;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IMethod;

public class NuclearStrikePropertyTester extends PropertyTester {

    public boolean test( Object receiver, String property, Object[] args, Object expectedValue ) {
        boolean result = false;
        if( receiver instanceof IMethod ){
            IMethod method = (IMethod)receiver;
            Job[] jobs = Job.getJobManager().find( NuclearStrikeJob.FAMILY );
            result = jobs.length > 0 && isNukeTargetInJob( method, jobs[0] );
        }
        trace( property, Boolean.valueOf( result ) );
        return result;
    }

    private boolean isNukeTargetInJob( IMethod method, Job job ) {
        boolean result = false;
        if( job instanceof NuclearStrikeJob ){
            NuclearStrikeJob nuclearStrikeJob = (NuclearStrikeJob)job;
            result = method.equals(  nuclearStrikeJob.getTarget());
        }
        return result;
    }

    private void trace( String property, Boolean result ) {
        String pattern = "Property {0} tested ({1})";
        NUCLEAR_STRIKE.trace( format( pattern, property, result)  );
    }
}
