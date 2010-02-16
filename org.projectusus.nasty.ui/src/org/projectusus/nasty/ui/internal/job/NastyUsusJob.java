// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.job;

import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.util.List;
import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.joda.time.DateTime;
import org.projectusus.nasty.ui.internal.IBadThingLoader;
import org.projectusus.nasty.ui.internal.IBadThingThatHappensPeriodically;

public class NastyUsusJob extends Job {

    private static final Random RND = new Random();
    private static final String[] WORDS = new String[] { "abominable", "devious", "diabolical", "disagreeable", "egregious", "iniquitous", "loathsome", "malicious",
            "mean-spirited", "nasty", "obnoxious", "really shabby", "unpleasant", "very bad", "vicious", "vile", "villainous", "wicked" };
    private final IBadThingLoader loader;

    public NastyUsusJob( IBadThingLoader loader ) {
        super( "Cooking up something " + getRandomNotion() + "." );
        this.loader = loader;
        setSystem( true );
        setUser( false );
        setPriority( INTERACTIVE );
    }

    @Override
    protected IStatus run( IProgressMonitor monitor ) {
        chooseABadThing().doIt( monitor );
        schedule( computeNextRun() );
        return OK_STATUS;
    }

    private IBadThingThatHappensPeriodically chooseABadThing() {
        List<IBadThingThatHappensPeriodically> badThings = loader.loadAllPeriodic();
        if( badThings.size() > 0 ) {
            int randomIndex = RND.nextInt( badThings.size() );
            return badThings.get( randomIndex );
        }
        return new NullBadThing();
    }

    private long computeNextRun() {
        return new DateTime().plusMinutes( RND.nextInt( 42 ) ).getMillis();
    }

    private static String getRandomNotion() {
        return WORDS[RND.nextInt( WORDS.length )];
    }

    private class NullBadThing implements IBadThingThatHappensPeriodically {
        public void doIt( IProgressMonitor monitor ) {
            // sadly, nothing diabolical to do
        }
    }
}
