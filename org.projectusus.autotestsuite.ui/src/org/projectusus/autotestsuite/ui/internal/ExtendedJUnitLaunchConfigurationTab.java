package org.projectusus.autotestsuite.ui.internal;

import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static org.eclipse.jdt.internal.junit.launcher.TestKindRegistry.getContainerTestKind;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.toProject;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.junit.launcher.TestKind;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
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
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationReader;
import org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationWriter;
import org.projectusus.autotestsuite.ui.internal.util.AutoTestSuiteUIImages;
import org.projectusus.autotestsuite.ui.internal.util.ISharedAutoTestSuiteImages;

import com.google.common.base.Function;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

    private Text projectText;
    private Button keepRunning;
    private ComboViewer testLoader;
    private CheckedProjectsViewer checkedProjectsViewer;

    public String getName() {
        return "Test Projects";
    }

    @Override
    public Image getImage() {
        return AutoTestSuiteUIImages.getSharedImages().getImage( ISharedAutoTestSuiteImages.OBJ_TAB );
    }

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

        createKeepRunningGroup( composite );
        Dialog.applyDialogFont( composite );
    }

    private void createCheckedProjectsSection( Composite composite ) {
        Label label = createLabel( composite, "&Selected Projects:" );
        GridData data = (GridData)label.getLayoutData();
        data.verticalAlignment = SWT.TOP;
        label.setLayoutData( data );

        checkedProjectsViewer = new CheckedProjectsViewer( composite );
        checkedProjectsViewer.addCheckStateListener( new ICheckStateListener() {
            public void checkStateChanged( CheckStateChangedEvent event ) {
                updateLaunchConfigurationDialog();
            }
        } );

        new MassSelectionComposite( composite ) {
            @Override
            void onSelectAll() {
                checkedProjectsViewer.setAllChecked( true );
                updateLaunchConfigurationDialog();
            }

            @Override
            void onDeselectAll() {
                checkedProjectsViewer.setAllChecked( false );
                updateLaunchConfigurationDialog();
            }
        };
    }

    private void createRootProjectSection( Composite composite ) {
        createLabel( composite, "Root &Project:" );

        projectText = new Text( composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY );
        projectText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        projectText.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent evt ) {
                updateLaunchConfigurationDialog();
                checkedProjectsViewer.updateCheckedProjects( getRootProject() );
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

    private IJavaProject getRootProject() {
        return toProject( projectText.getText().trim() );
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
        projectText.setText( project.getElementName() );
    }

    private IJavaProject chooseJavaProject() {
        ILabelProvider labelProvider = new JavaElementLabelProvider( JavaElementLabelProvider.SHOW_DEFAULT );
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( getShell(), labelProvider );
        dialog.setTitle( "Root Project Selection" );
        dialog.setMessage( "Choose a Root Project:" );
        dialog.setElements( new AllJavaProjectsInWorkspace().find() );

        IJavaProject javaProject = getRootProject();
        if( javaProject != null ) {
            dialog.setInitialSelections( new Object[] { javaProject } );
        }
        if( dialog.open() == Window.OK ) {
            return (IJavaProject)dialog.getFirstResult();
        }
        return null;
    }

    private void createSpacer( Composite composite ) {
        Label label = new Label( composite, SWT.NONE );
        GridData data = new GridData();
        data.horizontalSpan = 3;
        data.heightHint = 1;
        label.setLayoutData( data );
    }

    private void createKeepRunningGroup( Composite composite ) {
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

    private Label createLabel( Composite comp, String text ) {
        Label label = new Label( comp, SWT.NONE );
        label.setText( text );
        GridData data = new GridData();
        data.horizontalIndent = 0;
        label.setLayoutData( data );
        return label;
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
        ExtendedJUnitLaunchConfigurationWriter writer = new ExtendedJUnitLaunchConfigurationWriter( config );
        IJavaElement javaElement = getContext();
        if( javaElement != null ) {
            initializeJavaProject( writer, javaElement );
            writer.setCheckedProjects( asList( new AllJavaProjectsInWorkspace().find() ) );
        } else {
            // We set empty attributes for project & main type so that when one config is
            // compared to another, the existence of empty attributes doesn't cause an
            // incorrect result (the performApply() method can result in empty values
            // for these attributes being set on a config if there is nothing in the
            // corresponding text boxes)
            writer.setProjectName( "" );
            writer.setTestContainer( "" );
        }
        initializeTestAttributes( javaElement, writer );
    }

    private void initializeTestAttributes( IJavaElement javaElement, ExtendedJUnitLaunchConfigurationWriter writer ) {
        if( javaElement != null && javaElement.getElementType() < IJavaElement.COMPILATION_UNIT )
            initializeTestContainer( writer, javaElement );
        else
            initializeTestType( writer, javaElement );
    }

    private void initializeTestContainer( ExtendedJUnitLaunchConfigurationWriter config, IJavaElement javaElement ) {
        config.setTestContainer( javaElement.getHandleIdentifier() );
        initializeName( config, javaElement.getElementName() );
    }

    private void initializeName( ExtendedJUnitLaunchConfigurationWriter config, String inputName ) {
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
            config.renameTo( inputName );
        }
    }

    private void initializeTestType( ExtendedJUnitLaunchConfigurationWriter config, IJavaElement javaElement ) {
        if( javaElement instanceof ISourceReference ) {
            config.setTestKind( getContainerTestKind( javaElement ).getId() );
        }
        config.setUnusedAttributesToDefaults();
    }

    private void initializeJavaProject( ExtendedJUnitLaunchConfigurationWriter config, IJavaElement javaElement ) {
        IJavaProject javaProject = javaElement.getJavaProject();
        String name = null;
        if( javaProject != null && javaProject.exists() ) {
            name = javaProject.getElementName();
        }
        config.setProjectName( name );
    }

    public void initializeFrom( ILaunchConfiguration config ) {
        ExtendedJUnitLaunchConfigurationReader reader = new ExtendedJUnitLaunchConfigurationReader( config );
        projectText.setText( reader.getProjectName() );
        checkedProjectsViewer.setCheckedElements( reader.getCheckedProjects() );
        keepRunning.setSelection( reader.isKeepRunning() );
        testLoader.setSelection( new StructuredSelection( reader.getTestKind() ) );
    }

    public void performApply( ILaunchConfigurationWorkingCopy config ) {
        ExtendedJUnitLaunchConfigurationWriter writer = new ExtendedJUnitLaunchConfigurationWriter( config );
        writer.setUnusedAttributesToDefaults();
        writer.setProjectName( projectText.getText() );
        writer.setTestContainer( projectText.getText() );
        writer.setKeepRunning( keepRunning.getSelection() );
        applyCheckedProjects( writer );
        applyTestKind( writer );
    }

    private void applyTestKind( ExtendedJUnitLaunchConfigurationWriter writer ) {
        IStructuredSelection testKindSelection = (IStructuredSelection)testLoader.getSelection();
        if( !testKindSelection.isEmpty() ) {
            writer.setTestKind( ((TestKind)testKindSelection.getFirstElement()) );
        }
    }

    private void applyCheckedProjects( ExtendedJUnitLaunchConfigurationWriter writer ) {
        List<IJavaProject> checkedProjects = transform( asList( checkedProjectsViewer.getCheckedElements() ), new Function<Object, IJavaProject>() {
            public IJavaProject apply( Object object ) {
                return (IJavaProject)object;
            }
        } );
        writer.setCheckedProjects( checkedProjects );
    }

}
