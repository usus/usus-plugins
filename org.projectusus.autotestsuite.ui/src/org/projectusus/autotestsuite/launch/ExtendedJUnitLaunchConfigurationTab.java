package org.projectusus.autotestsuite.launch;

import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.jface.viewers.CheckboxTableViewer.newCheckList;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.loadCheckedProjects;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.saveCheckedProjects;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.toProject;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationDelegate.findRequiredProjects;
import static org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin.log;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.internal.junit.launcher.TestKind;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.internal.junit.util.TestSearchEngine;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.projectusus.autotestsuite.ui.internal.util.AutoTestSuiteUIImages;
import org.projectusus.autotestsuite.ui.internal.util.ISharedAutoTestSuiteImages;

import com.google.common.base.Function;

public class ExtendedJUnitLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

    private Text projectText;
    private Button keepRunning;
    private ComboViewer testLoader;
    private CheckboxTableViewer checkedProjectsViewer;

    public void createControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new GridLayout( 3, false ) );
        setControl( composite );

        createRootProjectSection( composite );
        createSpacer( composite );

        createCheckedProjectsSection( composite );
        createSpacer( composite );

        createTestLoaderGroup( composite );
        createSpacer( composite );

        createKeepAliveGroup( composite );
        Dialog.applyDialogFont( composite );
    }

    @Override
    public Image getImage() {
        return AutoTestSuiteUIImages.getSharedImages().getImage( ISharedAutoTestSuiteImages.OBJ_TAB );
    }

    private void createCheckedProjectsSection( Composite composite ) {
        Label label = createLabel( composite, "&Selected Projects:" );
        GridData data = (GridData)label.getLayoutData();
        data.verticalAlignment = SWT.TOP;
        label.setLayoutData( data );

        checkedProjectsViewer = newCheckList( composite, SWT.BORDER );
        checkedProjectsViewer.setContentProvider( new ArrayContentProvider() );
        checkedProjectsViewer.setLabelProvider( new JavaElementLabelProvider( JavaElementLabelProvider.SHOW_DEFAULT ) );
        checkedProjectsViewer.setInput( collectProjects() );
        checkedProjectsViewer.addCheckStateListener( new ICheckStateListener() {
            public void checkStateChanged( CheckStateChangedEvent event ) {
                updateLaunchConfigurationDialog();
            }
        } );

        data = new GridData( GridData.FILL_BOTH );
        checkedProjectsViewer.getControl().setLayoutData( data );

        Composite buttonPanel = new Composite( composite, SWT.NONE );
        GridLayout layout = new GridLayout( 1, false );
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        buttonPanel.setLayout( layout );
        data = new GridData();
        data.verticalAlignment = SWT.TOP;
        buttonPanel.setLayoutData( data );

        Button selectAllButton = new Button( buttonPanel, SWT.PUSH );
        selectAllButton.setText( "Select All" );
        selectAllButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent evt ) {
                checkedProjectsViewer.setAllChecked( true );
                updateLaunchConfigurationDialog();
            }
        } );
        setButtonGridData( selectAllButton );

        Button deselectAllButton = new Button( buttonPanel, SWT.PUSH );
        deselectAllButton.setText( "Deselect All" );
        deselectAllButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent evt ) {
                checkedProjectsViewer.setAllChecked( false );
                updateLaunchConfigurationDialog();
            }
        } );
        setButtonGridData( deselectAllButton );

    }

    private void createRootProjectSection( Composite composite ) {
        createLabel( composite, "Root &Project:" );

        projectText = new Text( composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY );
        projectText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        projectText.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent evt ) {
                updateLaunchConfigurationDialog();
                updateCheckedProjects();
            }
        } );

        Button projectButton = new Button( composite, SWT.PUSH );
        projectButton.setText( "&Browse..." );
        projectButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent evt ) {
                handleProjectButtonSelected();
            }
        } );
        setButtonGridData( projectButton );
    }

    private void updateCheckedProjects() {
        Object[] checked = checkedProjectsViewer.getCheckedElements();
        String projectName = projectText.getText();
        IJavaProject root = ExtendedJUnitLaunchConfigurationConstants.toProject( projectName );

        Set<IJavaProject> projects = new LinkedHashSet<IJavaProject>();
        if( root != null ) {
            projects.add( root );
            try {
                projects.addAll( findRequiredProjects( root ) );
            } catch( JavaModelException exception ) {
                log( exception );
            }
        }
        checkedProjectsViewer.setInput( projects );
        checkedProjectsViewer.setCheckedElements( checked );
    }

    private void setButtonGridData( Button button ) {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        button.setLayoutData( gridData );
    }

    private void handleProjectButtonSelected() {
        IJavaProject project = chooseJavaProject();
        if( project == null ) {
            return;
        }

        String projectName = project.getElementName();
        projectText.setText( projectName );
    }

    private IJavaProject chooseJavaProject() {
        IJavaProject[] projects = collectProjects();

        ILabelProvider labelProvider = new JavaElementLabelProvider( JavaElementLabelProvider.SHOW_DEFAULT );
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( getShell(), labelProvider );
        dialog.setTitle( "Root Project Selection" );
        dialog.setMessage( "Choose a Root Project:" );
        dialog.setElements( projects );

        IJavaProject javaProject = toProject( projectText.getText().trim() );
        if( javaProject != null ) {
            dialog.setInitialSelections( new Object[] { javaProject } );
        }
        if( dialog.open() == Window.OK ) {
            return (IJavaProject)dialog.getFirstResult();
        }
        return null;
    }

    private IJavaProject[] collectProjects() {
        IJavaProject[] projects;
        try {
            projects = JavaCore.create( getWorkspace().getRoot() ).getJavaProjects();
        } catch( JavaModelException e ) {
            projects = new IJavaProject[0];
        }
        return projects;
    }

    private void createSpacer( Composite composite ) {
        Label label = new Label( composite, SWT.NONE );
        GridData data = new GridData();
        data.horizontalSpan = 3;
        data.heightHint = 1;
        label.setLayoutData( data );
    }

    private void createKeepAliveGroup( Composite composite ) {
        keepRunning = new Button( composite, SWT.CHECK );
        keepRunning.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                updateLaunchConfigurationDialog();
            }
        } );
        keepRunning.setText( "&Keep JUnit running after a test run when debugging" );
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        keepRunning.setLayoutData( data );
    }

    private IJavaElement getContext() {
        IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if( activeWorkbenchWindow == null ) {
            return null;
        }
        IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
        if( page != null ) {
            ISelection selection = page.getSelection();
            if( selection instanceof IStructuredSelection ) {
                IStructuredSelection ss = (IStructuredSelection)selection;
                if( !ss.isEmpty() ) {
                    Object obj = ss.getFirstElement();
                    if( obj instanceof IJavaElement ) {
                        return (IJavaElement)obj;
                    }
                    if( obj instanceof IResource ) {
                        IJavaElement je = JavaCore.create( (IResource)obj );
                        if( je == null ) {
                            IProject pro = ((IResource)obj).getProject();
                            je = JavaCore.create( pro );
                        }
                        if( je != null ) {
                            return je;
                        }
                    }
                }
            }
            IEditorPart part = page.getActiveEditor();
            if( part != null ) {
                IEditorInput input = part.getEditorInput();
                return (IJavaElement)input.getAdapter( IJavaElement.class );
            }
        }
        return null;
    }

    public void setDefaults( ILaunchConfigurationWorkingCopy config ) {
        IJavaElement javaElement = getContext();
        if( javaElement != null ) {
            initializeJavaProject( javaElement, config );
            // TODO initialize checked projects
        } else {
            // We set empty attributes for project & main type so that when one config is
            // compared to another, the existence of empty attributes doesn't cause an
            // incorrect result (the performApply() method can result in empty values
            // for these attributes being set on a config if there is nothing in the
            // corresponding text boxes)
            config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "" ); //$NON-NLS-1$
            config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER, "" ); //$NON-NLS-1$
        }
        initializeTestAttributes( javaElement, config );
    }

    private void initializeTestAttributes( IJavaElement javaElement, ILaunchConfigurationWorkingCopy config ) {
        if( javaElement != null && javaElement.getElementType() < IJavaElement.COMPILATION_UNIT )
            initializeTestContainer( javaElement, config );
        else
            initializeTestType( javaElement, config );
    }

    private void initializeTestContainer( IJavaElement javaElement, ILaunchConfigurationWorkingCopy config ) {
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER, javaElement.getHandleIdentifier() );
        initializeName( config, javaElement.getElementName() );
    }

    private void initializeName( ILaunchConfigurationWorkingCopy config, String inputName ) {
        if( inputName == null ) {
            return;
        }
        if( inputName.length() > 0 ) {
            int index = inputName.lastIndexOf( '.' );
            String name = inputName;
            if( index > 0 ) {
                name = inputName.substring( index + 1 );
            }
            name = getLaunchConfigurationDialog().generateName( name );
            config.rename( inputName );
        }
    }

    private void initializeTestType( IJavaElement javaElement, ILaunchConfigurationWorkingCopy config ) {
        String testKindId = null;
        try {
            // we only do a search for compilation units or class files or
            // or source references
            if( javaElement instanceof ISourceReference ) {
                ITestKind testKind = TestKindRegistry.getContainerTestKind( javaElement );
                testKindId = testKind.getId();

                IType[] types = TestSearchEngine.findTests( getLaunchConfigurationDialog(), javaElement, testKind );
                if( (types == null) || (types.length < 1) ) {
                    return;
                }

            }
        } catch( InterruptedException ie ) {

        } catch( InvocationTargetException ite ) {
        }
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "" );
        if( testKindId != null ) {
            config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKindId );
        }
    }

    private void initializeJavaProject( IJavaElement javaElement, ILaunchConfigurationWorkingCopy config ) {
        IJavaProject javaProject = javaElement.getJavaProject();
        String name = null;
        if( javaProject != null && javaProject.exists() ) {
            name = javaProject.getElementName();
        }
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, name );
    }

    public void initializeFrom( ILaunchConfiguration config ) {
        updateProjectFrom( config );
        updateCheckedProjectsFrom( config );
        updateKeepRunning( config );
        updateTestLoaderFromConfig( config );
    }

    private void updateCheckedProjectsFrom( ILaunchConfiguration config ) {
        try {
            checkedProjectsViewer.setCheckedElements( loadCheckedProjects( config ) );
        } catch( CoreException exception ) {
            // ignore for now
        }
    }

    private void updateTestLoaderFromConfig( ILaunchConfiguration config ) {
        ITestKind testKind = JUnitLaunchConfigurationConstants.getTestRunnerKind( config );
        if( testKind.isNull() ) {
            testKind = TestKindRegistry.getDefault().getKind( TestKindRegistry.JUNIT4_TEST_KIND_ID );
        }
        testLoader.setSelection( new StructuredSelection( testKind ) );
    }

    private void updateKeepRunning( ILaunchConfiguration config ) {
        boolean running = false;
        try {
            running = config.getAttribute( JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING, false );
        } catch( CoreException exception ) {
            log( exception );
        }
        keepRunning.setSelection( running );
    }

    private void updateProjectFrom( ILaunchConfiguration config ) {
        String projectName = ""; //$NON-NLS-1$
        try {
            projectName = config.getAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "" ); //$NON-NLS-1$
        } catch( CoreException exception ) {
            log( exception );
        }
        projectText.setText( projectName );
    }

    public void performApply( ILaunchConfigurationWorkingCopy config ) {
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectText.getText() );
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER, projectText.getText() );
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "" ); //$NON-NLS-1$
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME, "" ); //$NON-NLS-1$
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING, keepRunning.getSelection() );
        applyCheckedProjects( config );
        IStructuredSelection testKindSelection = (IStructuredSelection)testLoader.getSelection();
        if( !testKindSelection.isEmpty() ) {
            TestKind testKind = (TestKind)testKindSelection.getFirstElement();
            config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKind.getId() );
        }
    }

    private void applyCheckedProjects( ILaunchConfigurationWorkingCopy config ) {
        List<IJavaProject> checkedProjects = transform( asList( checkedProjectsViewer.getCheckedElements() ), new Function<Object, IJavaProject>() {
            public IJavaProject apply( Object object ) {
                return (IJavaProject)object;
            }
        } );
        saveCheckedProjects( config, checkedProjects );
    }

    public String getName() {
        return "Root Test Project";
    }

    private void createTestLoaderGroup( Composite composite ) {
        createLabel( composite, "&Test runner:" );

        testLoader = new ComboViewer( composite, SWT.DROP_DOWN | SWT.READ_ONLY );
        GridData data = new GridData();
        data.horizontalSpan = 2;
        testLoader.getCombo().setLayoutData( data );

        testLoader.setContentProvider( new ArrayContentProvider() );
        testLoader.setLabelProvider( new LabelProvider() {
            @Override
            public String getText( Object element ) {
                return ((TestKind)element).getDisplayName();
            }
        } );
        testLoader.setInput( TestKindRegistry.getDefault().getAllKinds() );
        testLoader.addSelectionChangedListener( new ISelectionChangedListener() {
            public void selectionChanged( SelectionChangedEvent event ) {
                updateLaunchConfigurationDialog();
            }
        } );
    }

    protected Label createLabel( Composite comp, String text ) {
        Label label = new Label( comp, SWT.NONE );
        label.setText( text );
        GridData data = new GridData();
        data.horizontalIndent = 0;
        label.setLayoutData( data );
        return label;
    }

}
