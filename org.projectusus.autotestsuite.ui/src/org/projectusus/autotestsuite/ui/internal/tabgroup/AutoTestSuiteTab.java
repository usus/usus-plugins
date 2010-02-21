// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.tabgroup;

import static org.projectusus.autotestsuite.ui.internal.util.AutoTestSuiteUIImages.getSharedImages;
import static org.projectusus.autotestsuite.ui.internal.util.ISharedAutoTestSuiteImages.OBJ_TAB;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.internal.junit.launcher.TestKind;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.projectusus.autotestsuite.core.internal.ITestContainer;
import org.projectusus.autotestsuite.core.internal.ITestContainerGraph;
import org.projectusus.autotestsuite.core.internal.UsusTestContainerGraph;
import org.projectusus.autotestsuite.core.internal.WorkspaceTestContainerGraph;
import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

class AutoTestSuiteTab extends AbstractLaunchConfigurationTab {

    // TODO lf how are we going to do this? show a list of the root projects
    // for single-selection?

    private Button rbOnlyUsusProjects;
    private Button rbAllWsProjects;
    private Button cbKeepRunning;
    private ComboViewer cmbTestLoader;

    private ILaunchConfiguration launchConfig;

    public void createControl( Composite parent ) {
        Composite comp = createMainComposite( parent );
        rbAllWsProjects = initializeRadioButton( comp, "Run all tests in projects covered by Usus" );
        rbOnlyUsusProjects = initializeRadioButton( comp, "Run all tests in the workspace" );
        createSpacer( comp );
        createTestLoaderGroup( comp );
        createSpacer( comp );
        createKeepAliveGroup( comp );
        Dialog.applyDialogFont( comp );
        validatePage();
    }

    @Override
    public Image getImage() {
        return getSharedImages().getImage( OBJ_TAB );
    }

    @Override
    public boolean isValid( ILaunchConfiguration config ) {
        validatePage();
        return getErrorMessage() == null;
    }

    public String getName() {
        return "Automatic Test Suite";
    }

    public void initializeFrom( ILaunchConfiguration config ) {
        launchConfig = config;
        updateKeepRunning( config );
        updateTestLoaderFromConfig( config );
        validatePage();
    }

    public void setDefaults( ILaunchConfigurationWorkingCopy config ) {
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, getRootProjectName() );
        // TODO lf defaults for the other settings?
    }

    public void performApply( ILaunchConfigurationWorkingCopy config ) {
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING, cbKeepRunning.getSelection() );

        IStructuredSelection testKindSelection = (IStructuredSelection)cmbTestLoader.getSelection();
        if( !testKindSelection.isEmpty() ) {
            TestKind testKind = (TestKind)testKindSelection.getFirstElement();
            config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKind.getId() );
        }
        // workaround for bug 65399
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME, "" );
    }

    private String getRootProjectName() {
        ITestContainerGraph testContainerGraph;
        // TODO lf qnd
        if( rbOnlyUsusProjects != null && rbOnlyUsusProjects.getSelection() ) {
            testContainerGraph = new UsusTestContainerGraph();
        } else {
            testContainerGraph = new WorkspaceTestContainerGraph();
        }
        List<ITestContainer> rootContainers = testContainerGraph.getRootContainers();
        // TODO lf for the time being, restrict to a single root project; cases
        // - single project
        // - multiple projects, but can find single connected graph
        // - multiple graphs
        // -> can implement identification logic and support the first two
        // cases, need some UI and user decision for the last case
        if( !rootContainers.isEmpty() ) {
            return rootContainers.get( 0 ).getJavaElement().getElementName();
        }
        throw new IllegalStateException();
    }

    private void validatePage() {
        setErrorMessage( null );
        setMessage( null );
        new TestLoaderJVMValidator( launchConfig, getSelectedTestKind() ).validate();
    }

    // the ITestKind API is internal in JDT, no way to work around this
    @SuppressWarnings( "restriction" )
    private void updateTestLoaderFromConfig( ILaunchConfiguration config ) {
        ITestKind testKind = JUnitLaunchConfigurationConstants.getTestRunnerKind( config );
        if( testKind.isNull() ) {
            testKind = TestKindRegistry.getDefault().getKind( TestKindRegistry.JUNIT3_TEST_KIND_ID );
        }
        cmbTestLoader.setSelection( new StructuredSelection( testKind ) );
    }

    private TestKind getSelectedTestKind() {
        IStructuredSelection selection = (IStructuredSelection)cmbTestLoader.getSelection();
        return (TestKind)selection.getFirstElement();
    }

    private void updateKeepRunning( ILaunchConfiguration config ) {
        boolean running = false;
        try {
            running = config.getAttribute( "Keep JUnit running after a test when debugging", false );
        } catch( CoreException cex ) {
            AutoTestSuitePlugin.getDefault().getLog().log( cex.getStatus() );
        }
        cbKeepRunning.setSelection( running );
    }

    private void testModeChanged() {
        validatePage();
        updateLaunchConfigurationDialog();
    }

    private Composite createMainComposite( Composite parent ) {
        Composite result = new Composite( parent, SWT.NONE );
        result.setLayout( new GridLayout( 3, false ) );
        setControl( result );
        return result;
    }

    private void createTestLoaderGroup( Composite comp ) {
        Label loaderLabel = new Label( comp, SWT.NONE );
        loaderLabel.setText( "Test runner:" );
        GridData gd = new GridData();
        gd.horizontalIndent = 0;
        loaderLabel.setLayoutData( gd );

        cmbTestLoader = new ComboViewer( comp, SWT.DROP_DOWN | SWT.READ_ONLY );
        cmbTestLoader.getCombo().setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

        ArrayList/* <TestKind> */items = TestKindRegistry.getDefault().getAllKinds();
        cmbTestLoader.setContentProvider( new ArrayContentProvider() );
        cmbTestLoader.setLabelProvider( new LabelProvider() {
            @Override
            public String getText( Object element ) {
                return ((TestKind)element).getDisplayName();
            }
        } );
        cmbTestLoader.setInput( items );
        cmbTestLoader.addSelectionChangedListener( new ISelectionChangedListener() {
            public void selectionChanged( SelectionChangedEvent event ) {
                validatePage();
                updateLaunchConfigurationDialog();
            }
        } );
    }

    private void createSpacer( Composite comp ) {
        Label label = new Label( comp, SWT.NONE );
        GridData gd = new GridData();
        gd.horizontalSpan = 3;
        label.setLayoutData( gd );
    }

    private Button initializeRadioButton( Composite parent, String text ) {
        final Button result = new Button( parent, SWT.RADIO );
        result.setText( text );
        GridData gd = new GridData();
        gd.horizontalSpan = 3;
        result.setLayoutData( gd );
        result.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                if( result.getSelection() ) {
                    testModeChanged();
                }
            }
        } );
        result.setEnabled( false );
        return result;
    }

    private void createKeepAliveGroup( Composite comp ) {
        cbKeepRunning = new Button( comp, SWT.CHECK );
        cbKeepRunning.setText( "Keep JUnit running after a test when debugging" );
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.horizontalSpan = 2;
        cbKeepRunning.setLayoutData( gd );
        cbKeepRunning.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                updateLaunchConfigurationDialog();
            }
        } );
    }
}
