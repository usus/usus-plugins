// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.adapter.ForcedRecompute;

public class RefreshCockpit implements IViewActionDelegate {

    public void run( @SuppressWarnings( "unused" ) IAction action ) {
        new ForcedRecompute().schedule();
    }

    public void init( @SuppressWarnings( "unused" ) IViewPart view ) {
        // unused
    }

    public void selectionChanged( @SuppressWarnings( "unused" ) IAction action, @SuppressWarnings( "unused" ) ISelection selection ) {
        // unused
    }

}
