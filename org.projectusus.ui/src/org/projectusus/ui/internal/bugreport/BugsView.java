// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.widgets.Composite;
import org.projectusus.bugreport.core.Bug;
import org.projectusus.ui.internal.viewer.IColumnDesc;
import org.projectusus.ui.internal.viewer.TableViewPart;

public class BugsView extends TableViewPart<Bug> {

    @Override
    public void createPartControl( Composite parent ) {
        createViewer( parent );
        refresh();
        initOpenListener();
    }

    private void initOpenListener() {
        viewer.addOpenListener( new IOpenListener() {

            public void open( OpenEvent event ) {
                Bug bug = extractBug( event.getSelection() );
                if( bug != null ) {
                    openBugInEditor( bug );
                }
            }
        } );
    }

    private void openBugInEditor( Bug bug ) {

        try {
            IJavaModel model = JavaCore.create( getWorkspace().getRoot() );
            IJavaProject javaProject = model.getJavaProject( bug.getLocation().getProject() );
            IType type = javaProject.findType( bug.getLocation().getPackageName(), bug.getLocation().getClassName(), new NullProgressMonitor() );
            JavaUI.openInEditor( type );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    private Bug extractBug( ISelection selection ) {
        Bug result = null;
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            Object element = ((IStructuredSelection)selection).getFirstElement();
            if( element instanceof Bug ) {
                result = (Bug)element;
            }
        }
        return result;
    }

    @Override
    protected BugsContentProvider getContentProvider() {
        return new BugsContentProvider();
    }

    @Override
    protected BugsLP getLabelProvider() {
        return new BugsLP( asList( BugsColumnDesc.values() ) );
    }

    private void refresh() {
        IWorkspaceRoot input = getWorkspace().getRoot();
        viewer.setInput( input );
    }

    @Override
    protected IColumnDesc<Bug>[] getColumns() {
        return BugsColumnDesc.values();
    }

}
