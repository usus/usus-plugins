package org.projectusus.ui.internal.coveredprojects;

import static org.projectusus.core.project2.UsusProjectSupport.asUsusProject;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.projectusus.jfeet.selection.ElementsFrom;
import org.projectusus.ui.internal.UsusUIPlugin;

class SetUsusProjectOnSelection extends Action {

    static IAction checkSelected( CoveredProjectsView coveredProjectsView, ISelection selection ) {
        SetUsusProjectOnSelection setUsus = new SetUsusProjectOnSelection( coveredProjectsView, selection );
        setUsus.checked = true;
        setUsus.setText( "Add to Usus projects" ); //$NON-NLS-1$
        setUsus.setImageDescriptor( UsusUIPlugin.getImageDescriptor( "icons/full/elcl16/check.gif" ) ); //$NON-NLS-1$
        return setUsus;
    }

    static IAction uncheckSelected( CoveredProjectsView coveredProjectsView, ISelection selection ) {
        SetUsusProjectOnSelection setUsus = new SetUsusProjectOnSelection( coveredProjectsView, selection );
        setUsus.checked = false;
        setUsus.setText( "Remove from Usus projects" ); //$NON-NLS-1$
        setUsus.setImageDescriptor( UsusUIPlugin.getImageDescriptor( "icons/full/elcl16/uncheck.gif" ) ); //$NON-NLS-1$
        return setUsus;
    }

    private final CoveredProjectsView coveredProjectsView;
    private final ISelection selection;
    private boolean checked;

    private SetUsusProjectOnSelection( CoveredProjectsView coveredProjectsView, ISelection selection ) {
        this.coveredProjectsView = coveredProjectsView;
        this.selection = selection;
        updateEnablement();
    }

    @Override
    public void run() {
        for( IProject project : getSelectedProjects() ) {
            asUsusProject( project ).setUsusProject( checked );
        }
        coveredProjectsView.refresh();
    }

    private void updateEnablement() {
        setEnabled( !getSelectedProjects().isEmpty() );
    }

    private List<IProject> getSelectedProjects() {
        return new ElementsFrom( selection ).as( IProject.class );
    }
}
