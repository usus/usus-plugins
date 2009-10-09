// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.ui.internal.yellowcount.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.core.internal.yellowcount.CheckPointUtil;


/**
 * Action to delete the checkpoint log. A checkpoint is a snapshot of the current yellow count, together with a timestamp. There is a central log where checkpoints are stored.
 * 
 * @author Leif Frenzel
 */
public class DeleteCheckpoints implements IViewActionDelegate {

    public void run( IAction action ) {
        CheckPointUtil.deleteLog();
    }

    public void init( IViewPart view ) {
        // unused
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        // unused
    }
}
