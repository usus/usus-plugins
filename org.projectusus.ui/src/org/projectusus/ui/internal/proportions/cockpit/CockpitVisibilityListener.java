package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.projectusus.adapter.RunComputationOnResourceChange;

public class CockpitVisibilityListener implements IPartListener2 {

    private final CockpitView cockpitView;

    public CockpitVisibilityListener( CockpitView cockpitView ) {
        this.cockpitView = cockpitView;
    }

    public void partActivated( IWorkbenchPartReference partRef ) {
        // this only informs us about focus but not about visibility -> ignore it
    }

    public void partBroughtToTop( IWorkbenchPartReference partRef ) {
        // this is irrelevant to us
    }

    public void partClosed( IWorkbenchPartReference partRef ) {
        // closing the window is not interesting to us -> we need visibility
        setInvisible( partRef );
    }

    public void partDeactivated( IWorkbenchPartReference partRef ) {
        // this only informs us about focus but not about visibility -> ignore it
    }

    public void partOpened( IWorkbenchPartReference partRef ) {
        // opening the window is not interesting to us -> we need visibility
        setVisible( partRef );
    }

    public void partHidden( IWorkbenchPartReference partRef ) {
        // this is the super important notification!
        setInvisible( partRef );
    }

    public void partVisible( IWorkbenchPartReference partRef ) {
        // this is the super important notification!
        setVisible( partRef );
    }

    public void partInputChanged( IWorkbenchPartReference partRef ) {
        // this can / should not happen -> ignore it
    }

    private void setVisible( IWorkbenchPartReference partRef ) {
        if( partRef.getPart( false ) == cockpitView ) {
            RunComputationOnResourceChange.cockpitIsVisible();
        }
    }

    private void setInvisible( IWorkbenchPartReference partRef ) {
        if( partRef.getPart( false ) == cockpitView ) {
            RunComputationOnResourceChange.cockpitIsInvisible();
        }
    }
}
