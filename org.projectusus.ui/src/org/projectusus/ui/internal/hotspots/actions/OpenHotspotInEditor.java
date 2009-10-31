// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots.actions;

import static org.eclipse.jdt.core.JavaCore.createCompilationUnitFrom;
import static org.eclipse.jdt.ui.JavaUI.openInEditor;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.ui.internal.UsusUIPlugin;

public class OpenHotspotInEditor extends Action {

    private final IHotspot hotspot;

    public OpenHotspotInEditor( IHotspot hotspot ) {
        this.hotspot = hotspot;
    }

    @Override
    public void run() {
        openEditorAt( createCompilationUnitFrom( hotspot.getFile() ) );
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
        IEditorPart editor = openInEditor( compilationUnit );
        if( editor instanceof ITextEditor ) {
            openAtElement( hotspot, compilationUnit, editor );
        }
    }

    private void openAtElement( IHotspot hotspot, ICompilationUnit compilationUnit, IEditorPart editor ) throws Exception {
        ITextEditor textEditor = (ITextEditor)editor;
        IDocument document = textEditor.getDocumentProvider().getDocument( textEditor.getEditorInput() );
        if( document != null ) {
            int lineOffset = document.getLineOffset( hotspot.getLine() );
            IJavaElement element = compilationUnit.getElementAt( lineOffset );
            openInEditor( element, true, true );
        }
    }
}
