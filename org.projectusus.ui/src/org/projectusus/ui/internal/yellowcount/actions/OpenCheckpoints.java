// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.ui.internal.yellowcount.actions;

import static org.eclipse.jface.dialogs.MessageDialog.openInformation;
import static org.eclipse.swt.program.Program.findProgram;
import static org.eclipse.swt.program.Program.launch;
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.projectusus.core.internal.yellowcount.CheckPointUtil.getCheckpointsLog;
import static org.projectusus.ui.internal.util.UITexts.openCheckpoints_noLog_msg;
import static org.projectusus.ui.internal.util.UITexts.openCheckpoints_noLog_title;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Action to show the checkpoint log. A checkpoint is a snapshot of the current yellow count, together with a timestamp.There is a central log where checkpoints are stored.
 * 
 * @author Leif Frenzel
 */
public class OpenCheckpoints implements IViewActionDelegate {

    public void run( IAction action ) {
        if( getCheckpointsLog().exists() ) {
            String logFilePath = getCheckpointsLog().toString();
            boolean canLaunch = launch( logFilePath );
            if( !canLaunch ) {
                Program prog = findProgram( ".txt" ); //$NON-NLS-1$
                if( prog != null ) {
                    prog.execute( logFilePath );
                }
            }
        } else {
            Shell shell = getWorkbench().getActiveWorkbenchWindow().getShell();
            openInformation( shell, openCheckpoints_noLog_title, openCheckpoints_noLog_msg );
        }
    }

    public void init( IViewPart view ) {
        // unused
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        // unused
    }
}
