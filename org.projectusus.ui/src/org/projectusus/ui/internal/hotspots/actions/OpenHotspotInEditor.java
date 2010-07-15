// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.actions;

import static org.eclipse.jdt.core.JavaCore.createCompilationUnitFrom;
import static org.eclipse.jdt.ui.JavaUI.openInEditor;
import static org.eclipse.ui.PlatformUI.getWorkbench;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.ui.internal.UsusUIPlugin;

public class OpenHotspotInEditor extends Action {

    private final Hotspot hotspot;

    public OpenHotspotInEditor( Hotspot hotspot ) {
        this.hotspot = hotspot;
    }

    @Override
    public void run() {
        ICompilationUnit compilationUnit = extractCompilationUnit();
        if( compilationUnit != null ) {
            openEditorAt( compilationUnit );
        } else {
            openEditorAt( hotspot.getFile() );
        }
    }

    private void openEditorAt( IFile file ) {
        try {
            IDE.openEditor( getActivePage(), file );
        } catch( PartInitException paix ) {
            UsusUIPlugin.getDefault().getLog().log( paix.getStatus() );
        }
    }

    private ICompilationUnit extractCompilationUnit() {
        ICompilationUnit result = null;
        try {
            result = createCompilationUnitFrom( hotspot.getFile() );
        } catch( Exception ex ) {
            // The contract says that it returns null when a compilation unit
            // couldn't be loaded (e.g. it's a non-Java file); but actually,
            // the request fires an exception. Well, in any case, we know there
            // is no compilation unit...
        }
        return result;
    }

    private void openEditorAt( ICompilationUnit compilationUnit ) {
        if( compilationUnit != null ) {
            try {
                openJavaEditor( compilationUnit );
            } catch( Exception ex ) {
                UsusUIPlugin.getDefault().log( ex );
            }
        }
    }

    private void openJavaEditor( ICompilationUnit compilationUnit ) throws Exception {
        IJavaElement javaElement = compilationUnit.getElementAt( hotspot.getSourcePosition() );
        if( javaElement == null ) {
            javaElement = compilationUnit.getPrimaryElement();
        }
        if( javaElement != null ) {
            openInEditor( javaElement, true, true );
        }
    }

    private IWorkbenchPage getActivePage() {
        return getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
