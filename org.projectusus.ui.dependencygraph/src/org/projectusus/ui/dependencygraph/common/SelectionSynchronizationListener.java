package org.projectusus.ui.dependencygraph.common;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;

public class SelectionSynchronizationListener implements IPartListener2 {

    private final DependencyGraphView graphView;

    public SelectionSynchronizationListener( DependencyGraphView dependencyGraphView ) {
        this.graphView = dependencyGraphView;
    }

    public void partActivated( IWorkbenchPartReference partRef ) {
        synchronizeWith( partRef );
    }

    public void partBroughtToTop( IWorkbenchPartReference partRef ) {
        synchronizeWith( partRef );
    }

    public void partClosed( IWorkbenchPartReference partRef ) {
        // do nothing
    }

    public void partDeactivated( IWorkbenchPartReference partRef ) {
        // do nothing
    }

    public void partOpened( IWorkbenchPartReference partRef ) {
        synchronizeWith( partRef );
    }

    public void partHidden( IWorkbenchPartReference partRef ) {
        // do nothing
    }

    public void partVisible( IWorkbenchPartReference partRef ) {
        synchronizeWith( partRef );
    }

    public void partInputChanged( IWorkbenchPartReference partRef ) {
        synchronizeWith( partRef );
    }

    private void synchronizeWith( IWorkbenchPartReference partRef ) {
        if( partRef instanceof IEditorReference && isActive( partRef ) ) {
            IEditorPart editor = ((IEditorReference)partRef).getEditor( true );
            if( editor != null ) {
                graphView.selectNodeFromActiveEditor( editor );
            }
        }
    }

    public boolean isActive( IWorkbenchPartReference partRef ) {
        IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        return activePage != null && activePage.getActivePartReference() == partRef;
    }

}
