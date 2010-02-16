// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static org.projectusus.nasty.ui.internal.util.TracingOption.NUCLEAR_STRIKE;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.widgets.Display;

public class NuclearStrikeJob extends Job {

    private static final int GRACE_PERIOD_SECS = 12;

    public static final Object FAMILY = new Object();
    private final IMethod target;
    private final SwitchNamesRefactoring switchNamesRefactoring;
    private NuclearStrikeInfoDialog dialog;

    public NuclearStrikeJob( WSAnalysisResult analyzed ) {
        super( "Nuclear strike detected" );
        this.target = analyzed.getNukeTarget();
        this.switchNamesRefactoring = new SwitchNamesRefactoring( analyzed );
        setUser( true );
    }

    public IMethod getTarget() {
        return target;
    }

    @Override
    public boolean belongsTo( Object family ) {
        return family == FAMILY;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor ) {
        NUCLEAR_STRIKE.trace( getName() );
        try {
            openUI();
            waitForAGracePeriod();
            switchNamesRefactoring.execute();
        } catch( InterruptedException irex ) {
            // suppress
        } finally {
            killUI();
        }
        return Status.OK_STATUS;
    }

    private void waitForAGracePeriod() throws InterruptedException {
        for( int i = 0; i < GRACE_PERIOD_SECS; i++ ) {
            Thread.sleep( 1000 );
            int secondsToImpact = GRACE_PERIOD_SECS - i;
            updateUI( secondsToImpact );
            NUCLEAR_STRIKE.trace( "Time to impact: " + secondsToImpact );
        }
        updateUI( 0 ); // time's up, you're finished, dude
    }

    private void openUI() {
        runInUIThread( new Runnable() {
            public void run() {
                dialog = new NuclearStrikeInfoDialog( target.getElementName() );
                dialog.open();
            }
        } );
    }

    private void updateUI( final int secondsToImpact ) {
        runInUIThread( new Runnable() {
            public void run() {
                if( dialogStillThere() ) {
                    dialog.setCounterTo( secondsToImpact );
                }
            }
        } );
    }

    private void runInUIThread( Runnable op ) {
        Display.getDefault().syncExec( op );
    }

    private boolean dialogStillThere() {
        return dialog != null && dialog.getShell() != null && !dialog.getShell().isDisposed();
    }

    private void killUI() {
        runInUIThread( new Runnable() {
            public void run() {
                if( dialog != null && dialog.getShell() != null ) {
                    dialog.dispose();
                }
            }
        } );
    }
}
