// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static org.projectusus.core.internal.bugreport.SourceCodeLocation.getClazz;
import static org.projectusus.core.internal.bugreport.SourceCodeLocation.getMethod;
import static org.projectusus.core.internal.bugreport.SourceCodeLocation.getMethodLocation;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.project.NullUsusProject;
import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.FileRawData;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.projectusus.core.internal.proportions.sqi.MethodRawData;
import org.projectusus.ui.internal.UsusUIPlugin;

public class ReportBugAction extends Action implements IEditorActionDelegate {

    private ICompilationUnit selectedJavaClass;
    private IJavaElement selectedElement;

    public void setActiveEditor( IAction action, IEditorPart targetEditor ) {
        selectedJavaClass = null;
        selectedJavaClass = (ICompilationUnit)JavaUI.getEditorInputTypeRoot( targetEditor.getEditorInput() );

    }

    public void run( IAction action ) {
        if( getUsusProject().isUsusProject() && isMethodSelected() ) {

            ReportBugWizard wizard = new ReportBugWizard( initBugData() );
            WizardDialog dialog = new WizardDialog( Display.getCurrent().getActiveShell(), wizard );
            dialog.create();
            dialog.open();

            if( dialog.getReturnCode() == Window.OK ) {
                Bug bug = wizard.getBug();
                getUsusProject().saveBug( bug );
            }

        }
    }

    private Bug initBugData() {
        Bug bug = new Bug();
        try {
            IMethod method = getSelectedMethod();
            ClassRawData classRawData = getClassRawData( method );
            MethodRawData methodResults = classRawData.getResults( method );
            fillMethodMetrics( bug, methodResults );
            fillClassMetrics( bug, classRawData );
            bug.setLocation( getMethodLocation( method ) );
        } catch( JavaModelException e ) {
            UsusUIPlugin.getDefault().log( e );
        }

        return bug;
    }

    private void fillClassMetrics( Bug bug, ClassRawData classRawData ) {
        int numberOfMethods = classRawData.getViolationBasis( IsisMetrics.CC );
        bug.getBugMetrics().setNumberOfMethods( numberOfMethods );
    }

    private void fillMethodMetrics( Bug bug, MethodRawData methodResults ) {
        bug.getBugMetrics().setCyclomaticComplexity( methodResults.getCCResult() );
        bug.getBugMetrics().setMethodLength( methodResults.getMLResult() );
    }

    private ClassRawData getClassRawData( IMethod method ) throws JavaModelException {
        // TODO: use method.getDeclaringType
        IJavaElement clazz = getClazz( method );
        IUSUSProject ususProject = getUsusProject();
        FileRawData fileResults = ususProject.getProjectResults().getFileResults( (IFile)selectedJavaClass.getUnderlyingResource() );
        ClassRawData classResults = fileResults.getResults( clazz );
        return classResults;
    }

    private IMethod getSelectedMethod() {
        return getMethod( selectedElement );
    }

    private boolean isMethodSelected() {
        return getSelectedMethod() != null;
    }

    private IUSUSProject getUsusProject() {
        if( selectedJavaClass == null ) {
            return new NullUsusProject();
        }
        IJavaProject project = selectedJavaClass.getJavaProject();
        return (IUSUSProject)project.getProject().getAdapter( IUSUSProject.class );
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        selectedElement = null;
        if( selection instanceof TextSelection ) {
            TextSelection textSelection = (TextSelection)selection;
            int offset = textSelection.getOffset();
            try {
                selectedElement = selectedJavaClass.getElementAt( offset );
            } catch( JavaModelException e ) {
                UsusUIPlugin.getDefault().log( e );
            }
        }
    }
}
