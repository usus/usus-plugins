// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.ui.internal.yellowcount.actions;

import java.util.Date;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.core.internal.yellowcount.CheckPointUtil;
import org.projectusus.core.internal.yellowcount.IYellowCountResult;
import org.projectusus.core.internal.yellowcount.ResultFormatter;
import org.projectusus.core.internal.yellowcount.YellowCount;

/**
 * Action to add a checkpoint to the log. A checkpoint is a snapshot of the current yellow count, together with a timestamp. There is a central log where checkpoints are stored.
 * 
 * @author Leif Frenzel
 */
public class AddCheckpoint implements IViewActionDelegate {

    public void run( IAction action ) {
        IYellowCountResult count = YellowCount.getInstance().count();
        String checkpoint = new Date().toString() + " " + new ResultFormatter().dump( count ) + "\r\n"; //$NON-NLS-1$ //$NON-NLS-2$
        CheckPointUtil.addCheckpoint( checkpoint );
    }

    public void init( IViewPart view ) {
        // unused
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        // unused
    }
}
