// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.ui.internal;

import static org.projectusus.bugprison.core.SourceCodeLocation.getMethod;
import static org.projectusus.bugprison.core.SourceCodeLocation.getMethodLocation;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.projectusus.bugprison.core.Bug;
import org.projectusus.bugprison.core.IBuggyProject;
import org.projectusus.bugprison.core.NullBuggyProject;
import org.projectusus.core.IMetricsAccessor;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.project.NullUsusProject;
import org.projectusus.core.internal.proportions.rawdata.MethodVisitor;
import org.projectusus.ui.internal.UsusUIPlugin;
import org.projectusus.ui.internal.selection.EditorInputAnalysis;
import org.projectusus.ui.internal.selection.JDTWorkspaceEditorInputAnalysis;

public class ReportBugAction extends Action implements IEditorActionDelegate {

    private static final IMetricsAccessor metrics = UsusCorePlugin.getMetricsAccessor();
    private ICompilationUnit selectedJavaClass;
    private IJavaElement selectedElement;

    public void setActiveEditor( IAction action, IEditorPart targetEditor ) {
        // do nothing
    }

    public void run( IAction action ) {
        init();

        if( getUsusProject().isUsusProject() && isMethodSelected() ) {

            ReportBugWizard wizard = new ReportBugWizard( initBugData() );
            WizardDialog dialog = new WizardDialog( Display.getCurrent().getActiveShell(), wizard );
            dialog.create();
            dialog.open();

            if( dialog.getReturnCode() == Window.OK ) {
                Bug bug = wizard.getBug();
                getBuggyProject().saveBug( bug );
            }

        }
    }

    private void init() {
        try {
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IEditorInput editorInput = workbenchWindow.getActivePage().getActiveEditor().getEditorInput();

            EditorInputAnalysis analysis = new JDTWorkspaceEditorInputAnalysis( editorInput );
            selectedJavaClass = analysis.getCompilationUnit();
            ISelection selection = workbenchWindow.getSelectionService().getSelection();
            selectedElement = analysis.getSelectedElement( selection );
        } catch( Exception e ) {
            UsusUIPlugin.getDefault().log( e );
        }
    }

    private Bug initBugData() {
        Bug bug = new Bug();
        initBugClassData( bug );
        return bug;
    }

    private void initBugClassData( Bug bug ) {
        fillClassMetrics( bug );
        fillMethodMetrics( bug, getSelectedMethod() );
        bug.setLocation( getMethodLocation( getSelectedMethod() ) );
    }

    private void fillClassMetrics( Bug bug ) {
        int numberOfMethods = metrics.getNumberOf( CodeProportionUnit.METHOD );
        bug.getBugMetrics().setNumberOfMethods( numberOfMethods );
    }

    private void fillMethodMetrics( Bug bug, IMethod method ) {
        try {
            MethodVisitor visitor = new MethodVisitor( method );
            bug.getBugMetrics().setCyclomaticComplexity( visitor.getCCValue() );
            bug.getBugMetrics().setMethodLength( visitor.getMLValue() );
        } catch( JavaModelException e ) {
            // do nothing
        }
    }

    private IMethod getSelectedMethod() {
        return getMethod( selectedElement );
    }

    private boolean isMethodSelected() {
        return getSelectedMethod() != null;
    }

    private IBuggyProject getBuggyProject() {
        if( selectedJavaClass == null ) {
            return new NullBuggyProject();
        }
        IJavaProject project = selectedJavaClass.getJavaProject();
        return (IBuggyProject)project.getProject().getAdapter( IBuggyProject.class );
    }

    private IUSUSProject getUsusProject() {
        if( selectedJavaClass == null ) {
            return new NullUsusProject();
        }
        IJavaProject project = selectedJavaClass.getJavaProject();
        return (IUSUSProject)project.getProject().getAdapter( IUSUSProject.class );
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        // do nothing
    }
}
