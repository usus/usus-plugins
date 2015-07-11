// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.projectusus.projectsettings.core.WhichPrefs;

public class SelectProjectPage extends WizardPage {

    private final List<IProject> projects;
    private IProject selectedProject;
    private List<WhichPrefs> whichPrefs = new ArrayList<WhichPrefs>();

    public SelectProjectPage( List<IProject> projects ) {
        super( "SelectProjectPage" ); //$NON-NLS-1$
        this.projects = projects;
        setTitle( "Select Project" ); //$NON-NLS-1$
        setDescription( getDescriptionText() );
    }

    protected String getDescriptionText() {
        return "Select a master project. Settings will be copied to the other projects."; //$NON-NLS-1$
    }

    public void createControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NULL );
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        composite.setLayout( gridLayout );
        Label label = new Label( composite, SWT.NULL );
        label.setText( getDescriptionText() );
        createCheckbox( composite, "Copy Compiler Warnings Preferences", WhichPrefs.CompilerWarnings );
        createCheckbox( composite, "Copy Codecompletion Preferences", WhichPrefs.CodeCompletion );
        createCheckbox( composite, "Copy Formatting Preferences", WhichPrefs.Formatting );
        createProjectsList( composite );
        setControl( composite );
    }

    private void createCheckbox( Composite composite, String label, WhichPrefs whichPref ) {
        Button checkbox = new Button( composite, SWT.CHECK );
        checkbox.setText( label );
        GridData gridData = new GridData();
        checkbox.setLayoutData( gridData );
        checkbox.addSelectionListener( new SelectWhichListener( checkbox, getWizard(), whichPrefs, whichPref ) );
    }

    private void createProjectsList( Composite composite ) {
        ListViewer list = new ListViewer( composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
        GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, true );
        list.getList().setLayoutData( gridData );
        list.setContentProvider( new ArrayContentProvider() );
        list.setLabelProvider( new ProjectsLabelProvider() );
        list.setInput( projects );
        initListener( list );
    }

    private void initListener( ListViewer list ) {
        list.addSelectionChangedListener( new ISelectionChangedListener() {

            public void selectionChanged( SelectionChangedEvent event ) {
                IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                setSelectedProject( (IProject)selection.getFirstElement() );
                getWizard().getContainer().updateButtons();
            }
        } );
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

    public WhichPrefs[] getWhichPrefs() {
        return whichPrefs.toArray( new WhichPrefs[whichPrefs.size()] );
    }

}
