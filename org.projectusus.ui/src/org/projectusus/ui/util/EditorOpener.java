package org.projectusus.ui.util;

import static org.eclipse.jdt.ui.JavaUI.openInEditor;
import static org.eclipse.ui.PlatformUI.getWorkbench;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.projectusus.ui.internal.UsusUIPlugin;

public final class EditorOpener {

    public void openEditor( IFile file ) {
        if( file == null ) {
            return;
        }
        try {
            IDE.openEditor( getActivePage(), file );
        } catch( PartInitException paix ) {
            UsusUIPlugin.getDefault().getLog().log( paix.getStatus() );
        }
    }

    public void openEditorAt( ICompilationUnit compilationUnit, int sourcePosition ) {
        if( compilationUnit != null ) {
            try {
                openJavaEditor( compilationUnit, sourcePosition );
            } catch( Exception ex ) {
                UsusUIPlugin.getDefault().log( ex );
            }
        }
    }

    private void openJavaEditor( ICompilationUnit compilationUnit, int sourcePosition ) throws Exception {
        IJavaElement javaElement = compilationUnit.getElementAt( sourcePosition );
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
