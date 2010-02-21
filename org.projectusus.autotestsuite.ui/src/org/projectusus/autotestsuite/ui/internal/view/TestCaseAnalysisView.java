// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.view;

import static org.eclipse.jface.viewers.AbstractTreeViewer.ALL_LEVELS;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.autotestsuite.core.internal.TestContainerGraph;
import org.projectusus.autotestsuite.core.internal.WorkspaceTestContainerGraph;

public class TestCaseAnalysisView extends ViewPart {

    private TreeViewer treeViewer;

    @Override
    public void createPartControl( Composite parent ) {
        treeViewer = new TreeViewer( parent );
        treeViewer.setAutoExpandLevel( ALL_LEVELS );
        treeViewer.setLabelProvider( new TestCaseAnalysisLP() );
        treeViewer.setContentProvider( new TestCaseAnalysisCP() );
        setInput( new WorkspaceTestContainerGraph() );
    }

    @Override
    public void setFocus() {
        if( treeViewer != null && !treeViewer.getControl().isDisposed() ) {
            treeViewer.getControl().setFocus();
        }
    }

    public void setInput( TestContainerGraph input ) {
        if( treeViewer != null ) {
            treeViewer.setInput( input );
        }
    }
}
