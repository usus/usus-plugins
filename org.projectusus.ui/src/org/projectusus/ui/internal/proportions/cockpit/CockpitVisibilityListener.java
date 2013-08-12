package org.projectusus.ui.internal.proportions.cockpit;

import org.eclipse.ui.IWorkbenchPartReference;
import org.projectusus.adapter.RunComputationOnResourceChange;
import org.projectusus.ui.util.PartAdapter;

public class CockpitVisibilityListener extends PartAdapter {

    private final CockpitView cockpitView;

    public CockpitVisibilityListener( CockpitView cockpitView ) {
        this.cockpitView = cockpitView;
    }

    @Override
    public void partClosed( IWorkbenchPartReference partRef ) {
        setInvisible( partRef );
    }

    @Override
    public void partOpened( IWorkbenchPartReference partRef ) {
        setVisible( partRef );
    }

    @Override
    public void partHidden( IWorkbenchPartReference partRef ) {
        // this is the super important notification!
        setInvisible( partRef );
    }

    @Override
    public void partVisible( IWorkbenchPartReference partRef ) {
        // this is the super important notification!
        setVisible( partRef );
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
