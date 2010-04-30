// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.projectusus.ui.internal.UsusUIPlugin;

public class EditorInputAnalysis {

    private final ICompilationUnit compilationUnit;

    EditorInputAnalysis( ICompilationUnitInputLoader loader, IEditorInput editorInput ) {
        if( loader == null ) {
            throw new IllegalArgumentException();
        }
        compilationUnit = loader.loadCompilationUnit( editorInput );
    }

    public ICompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public IJavaElement getSelectedElement( ISelection selection ) {
        if( selection instanceof TextSelection ) {
            try {
                IJavaElement element = compilationUnit.getElementAt( ((TextSelection)selection).getOffset() );
                return element == null ? compilationUnit : element;
            } catch( JavaModelException jamox ) {
                UsusUIPlugin.getDefault().log( jamox );
            }
        }
        return compilationUnit;
    }

}
