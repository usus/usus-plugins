// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import static org.projectusus.projectsettings.ui.internal.UITexts.SelectProjectPage_description;
import static org.projectusus.projectsettings.ui.internal.UITexts.SelectProject_title;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SelectProjectPage extends WizardPage {

    private final List<IProject> projects;
    private IProject selectedProject;

    public SelectProjectPage( List<IProject> projects ) {
        super( "SelectProjectPage" ); //$NON-NLS-1$
        this.projects = projects;
        setTitle( SelectProject_title );
        setDescription( SelectProjectPage_description );
    }

    public void createControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NULL );
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        composite.setLayout( gridLayout );
        Label label = new Label( composite, SWT.NULL );
        label.setText( SelectProjectPage_description );
        ListViewer list = new ListViewer( composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
        GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, true );
        list.getList().setLayoutData( gridData );
        list.setContentProvider( new ArrayContentProvider() );
        list.setLabelProvider( new ProjectsLabelProvider() );
        list.setInput( projects );
        list.addSelectionChangedListener( new ISelectionChangedListener() {

            public void selectionChanged( SelectionChangedEvent event ) {
                IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                setSelectedProject( (IProject)selection.getFirstElement() );
                getWizard().getContainer().updateButtons();
            }
        } );
        setControl( composite );
    }

    public IProject getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject( IProject selectedProject ) {
        this.selectedProject = selectedProject;
    }

    public boolean isProjectSelected() {
        return getSelectedProject() != null;
    }

}
