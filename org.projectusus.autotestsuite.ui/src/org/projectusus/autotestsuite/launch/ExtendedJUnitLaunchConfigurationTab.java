package org.projectusus.autotestsuite.launch;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.lang.reflect.InvocationTargetException;

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
import org.eclipse.jdt.internal.junit.launcher.JUnitMigrationDelegate;
import org.eclipse.jdt.internal.junit.launcher.TestKind;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.internal.junit.ui.JUnitMessages;
import org.eclipse.jdt.internal.junit.ui.JUnitPlugin;
import org.eclipse.jdt.internal.junit.util.TestSearchEngine;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
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

public class ExtendedJUnitLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

    private Text projectText;
    private Button projectButton;
    private Button keepRunning;
    private ComboViewer testLoader;

    public void createControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new GridLayout( 3, false ) );
        setControl( composite );

        createSingleTestSection( composite );
        createSpacer( composite );

        createTestLoaderGroup( composite );
        createSpacer( composite );

        createKeepAliveGroup( composite );
        Dialog.applyDialogFont( composite );
    }

    @SuppressWarnings( "restriction" )
    private void createSingleTestSection( Composite composite ) {
        createLabel( composite, "Root &Project:" );

        projectText = new Text( composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY );
        projectText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        projectText.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent evt ) {
                updateLaunchConfigurationDialog();
            }
        } );

        projectButton = new Button( composite, SWT.PUSH );
        projectButton.setText( JUnitMessages.JUnitLaunchConfigurationTab_label_browse );
        projectButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent evt ) {
                handleProjectButtonSelected();
            }
        } );
        setButtonGridData( projectButton );
    }

    private void setButtonGridData( Button button ) {
        GridData gridData = new GridData();
        button.setLayoutData( gridData );
        // LayoutUtil.setButtonDimensionHint( button );
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
        IJavaProject[] projects;
        try {
            projects = JavaCore.create( getWorkspace().getRoot() ).getJavaProjects();
        } catch( JavaModelException e ) {
            projects = new IJavaProject[0];
        }

        ILabelProvider labelProvider = new JavaElementLabelProvider( JavaElementLabelProvider.SHOW_DEFAULT );
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( getShell(), labelProvider );
        dialog.setTitle( "Root Project Selection" );
        dialog.setMessage( "Choose a Root Project:" );
        dialog.setElements( projects );

        IJavaProject javaProject = getJavaProject();
        if( javaProject != null ) {
            dialog.setInitialSelections( new Object[] { javaProject } );
        }
        if( dialog.open() == Window.OK ) {
            return (IJavaProject)dialog.getFirstResult();
        }
        return null;
    }

    private IJavaProject getJavaProject() {
        String projectName = projectText.getText().trim();
        if( projectName.length() < 1 ) {
            return null;
        }
        return JavaCore.create( getWorkspace().getRoot() ).getJavaProject( projectName );
    }

    private void createSpacer( Composite composite ) {
        Label label = new Label( composite, SWT.NONE );
        GridData data = new GridData();
        data.horizontalSpan = 3;
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
        keepRunning.setText( JUnitMessages.JUnitLaunchConfigurationTab_label_keeprunning );
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
        String name = ""; //$NON-NLS-1$
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
                // Simply grab the first main type found in the searched element
                name = types[0].getFullyQualifiedName( '.' );

            }
        } catch( InterruptedException ie ) {

        } catch( InvocationTargetException ite ) {
        }
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, name );
        if( testKindId != null )
            config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKindId );
        initializeName( config, name );
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
        updateProjectFromConfig( config );
        String containerHandle = ""; //$NON-NLS-1$
        try {
            containerHandle = config.getAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER, "" ); //$NON-NLS-1$
        } catch( CoreException ce ) {
        }

        updateKeepRunning( config );
        updateTestLoaderFromConfig( config );
    }

    private void updateTestLoaderFromConfig( ILaunchConfiguration config ) {
        ITestKind testKind = JUnitLaunchConfigurationConstants.getTestRunnerKind( config );
        if( testKind.isNull() )
            testKind = TestKindRegistry.getDefault().getKind( TestKindRegistry.JUNIT3_TEST_KIND_ID );
        testLoader.setSelection( new StructuredSelection( testKind ) );
    }

    private void updateKeepRunning( ILaunchConfiguration config ) {
        boolean running = false;
        try {
            running = config.getAttribute( JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING, false );
        } catch( CoreException ce ) {
        }
        keepRunning.setSelection( running );
    }

    private void updateProjectFromConfig( ILaunchConfiguration config ) {
        String projectName = ""; //$NON-NLS-1$
        try {
            projectName = config.getAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "" ); //$NON-NLS-1$
        } catch( CoreException ce ) {
        }
        projectText.setText( projectName );
    }

    public void performApply( ILaunchConfigurationWorkingCopy config ) {
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectText.getText() );
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER, projectText.getText() );
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "" ); //$NON-NLS-1$
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME, "" ); //$NON-NLS-1$
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING, keepRunning.getSelection() );
        try {
            mapResources( config );
        } catch( CoreException e ) {
            JUnitPlugin.log( e.getStatus() );
        }
        IStructuredSelection testKindSelection = (IStructuredSelection)testLoader.getSelection();
        if( !testKindSelection.isEmpty() ) {
            TestKind testKind = (TestKind)testKindSelection.getFirstElement();
            config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKind.getId() );
        }
    }

    private void mapResources( ILaunchConfigurationWorkingCopy config ) throws CoreException {
        JUnitMigrationDelegate.mapResources( config );
    }

    public String getName() {
        return "Root Test Project";
    }

    @SuppressWarnings( "restriction" )
    private void createTestLoaderGroup( Composite composite ) {
        createLabel( composite, JUnitMessages.JUnitLaunchConfigurationTab_Test_Loader );

        testLoader = new ComboViewer( composite, SWT.DROP_DOWN | SWT.READ_ONLY );
        testLoader.getCombo().setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

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

    protected void createLabel( Composite comp, String text ) {
        Label label = new Label( comp, SWT.NONE );
        label.setText( text );
        GridData data = new GridData();
        data.horizontalIndent = 0;
        label.setLayoutData( data );
    }

}
