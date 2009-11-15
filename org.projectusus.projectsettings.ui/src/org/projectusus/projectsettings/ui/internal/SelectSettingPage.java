// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import java.util.List;

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
import org.projectusus.core.projectsettings.ProjectSettings;

public class SelectSettingPage extends WizardPage {

    private final List<ProjectSettings> projectSettings;
    private ProjectSettings selectedSetting;

    protected SelectSettingPage( List<ProjectSettings> projectSettings ) {
        super( "" ); //$NON-NLS-1$
        this.projectSettings = projectSettings;
        setTitle( UITexts.SelectSettingPage_title );
        setDescription( UITexts.SelectSettingPage_description );
    }

    public void createControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NULL );
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        composite.setLayout( gridLayout );
        Label label = new Label( composite, SWT.NULL );
        label.setText( UITexts.SelectSettingPage_available_setting );
        ListViewer list = new ListViewer( composite, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
        GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, true );
        list.getList().setLayoutData( gridData );
        list.setContentProvider( new ArrayContentProvider() );
        list.setLabelProvider( new SettingsLabelProvider() );
        list.setInput( projectSettings );
        list.addSelectionChangedListener( new ISelectionChangedListener() {

            public void selectionChanged( SelectionChangedEvent event ) {
                IStructuredSelection selection = (IStructuredSelection)event.getSelection();
                setSelectedSetting( (ProjectSettings)selection.getFirstElement() );
                getWizard().getContainer().updateButtons();
            }
        } );
        setControl( composite );
    }

    public ProjectSettings getSelectedSetting() {
        return selectedSetting;
    }

    public void setSelectedSetting( ProjectSettings selectedSetting ) {
        this.selectedSetting = selectedSetting;
    }

    public boolean isProjectSelected() {
        return getSelectedSetting() != null;
    }
}
