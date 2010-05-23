// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.view.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.autotestsuite.core.internal.TestContainerGraph;
import org.projectusus.autotestsuite.core.internal.WorkspaceTestContainerGraph;
import org.projectusus.autotestsuite.ui.internal.view.TestCaseAnalysisView;

public class Refresh implements IViewActionDelegate {

    private IViewPart view;

    public void init( IViewPart viewPart ) {
        this.view = viewPart;
    }

    public void run( IAction action ) {
        if( view instanceof TestCaseAnalysisView ) {
            TestContainerGraph newInput = new WorkspaceTestContainerGraph();
            ((TestCaseAnalysisView)view).setInput( newInput );
        }
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        // unused
    }
}
