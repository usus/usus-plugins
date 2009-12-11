// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
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

    public IMethod getSelectedMethod( ISelection selection ) {
        return extractSelectedElement( selection );
    }

    private IMethod extractSelectedElement( ISelection selection ) {
        IMethod result = null;
        if( selection instanceof TextSelection ) {
            result = extractSelectedMethod( compilationUnit, (TextSelection)selection );
        }
        return result;
    }

    private IMethod extractSelectedMethod( ICompilationUnit compilationUnit, TextSelection textSelection ) {
        IMethod result = null;
        try {
            int offset = textSelection.getOffset();
            IJavaElement element = compilationUnit.getElementAt( offset );
            if( element instanceof IMethod ) {
                result = (IMethod)element;
            }
        } catch( JavaModelException jamox ) {
            UsusUIPlugin.getDefault().log( jamox );
        }
        return result;
    }
}
