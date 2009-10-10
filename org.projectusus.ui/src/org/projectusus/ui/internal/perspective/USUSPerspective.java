// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.perspective;

import static org.eclipse.ui.IPageLayout.BOTTOM;
import static org.eclipse.ui.IPageLayout.LEFT;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.projectusus.ui.internal.coveredprojects.CoveredProjectsView;
import org.projectusus.ui.internal.history.HistoryView;
import org.projectusus.ui.internal.proportions.CodeProportionsCockpit;
import org.projectusus.ui.internal.yellowcount.YellowCountView;

public class USUSPerspective implements IPerspectiveFactory {

    public void createInitialLayout( IPageLayout layout ) {
        layoutViews( layout );
        createViewShortcuts( layout );
        createPerspectiveShortcuts( layout );
    }

    private void layoutViews( IPageLayout layout ) {
        // left-hand side
        IFolderLayout projects = layout.createFolder( "projects", LEFT, 0.4f, layout.getEditorArea() );
        projects.addView( "org.eclipse.jdt.ui.PackageExplorer" );
        projects.addView( CoveredProjectsView.class.getName() );
        layout.addView( CodeProportionsCockpit.class.getName(), BOTTOM, 0.5f, "projects" );
        layout.addView( YellowCountView.class.getName(), BOTTOM, 0.8f, CodeProportionsCockpit.class.getName() );

        // right-hand side
        layout.addView( HistoryView.class.getName(), BOTTOM, 0.33f, layout.getEditorArea() );
        IFolderLayout inputs = layout.createFolder( "inputs", BOTTOM, 0.5f, HistoryView.class.getName() );
        inputs.addView( "org.eclipse.ui.views.TaskList" );
        inputs.addView( "org.eclipse.ui.views.ProblemView" );
        inputs.addView( "org.eclipse.jdt.junit.ResultView" );
        inputs.addView( "com.mountainminds.eclemma.ui.CoverageView" );
    }

    private void createViewShortcuts( IPageLayout layout ) {
        layout.addShowViewShortcut( HistoryView.class.getName() );
        layout.addShowViewShortcut( CodeProportionsCockpit.class.getName() );
        layout.addShowViewShortcut( YellowCountView.class.getName() );
        layout.addShowViewShortcut( CoveredProjectsView.class.getName() );

        layout.addShowViewShortcut( "org.eclipse.ui.views.TaskList" );
        layout.addShowViewShortcut( "org.eclipse.ui.views.ProblemView" );
        layout.addShowViewShortcut( "org.eclipse.jdt.junit.ResultView" );
        layout.addShowViewShortcut( "com.mountainminds.eclemma.ui.CoverageView" );
        layout.addShowViewShortcut( "org.eclipse.jdt.ui.PackageExplorer" );
    }

    private void createPerspectiveShortcuts( IPageLayout layout ) {
        layout.addPerspectiveShortcut( "org.eclipse.jdt.ui.JavaPerspective" );
        layout.addPerspectiveShortcut( "org.eclipse.pde.ui.PDEPerspective" );
    }
}
