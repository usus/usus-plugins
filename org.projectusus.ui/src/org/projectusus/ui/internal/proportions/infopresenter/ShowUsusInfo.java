// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.ui.internal.selection.EditorInputAnalysis;
import org.projectusus.ui.internal.selection.JDTWorkspaceEditorInputAnalysis;

public class ShowUsusInfo extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        IEditorPart activeEditor = HandlerUtil.getActiveEditor( event );
        if( activeEditor != null ) {
            // TODO CCD session continue here:
            // probably we want to use the method object, get some Usus info
            // related to it, and present the stuff on the lightweight dialog
            IJavaElement method = extractSelectedMethod( event, activeEditor );
            LightWeightDialog dialog = new LightWeightDialog( HandlerUtil.getActiveShell( event ) );
            dialog.setInput( method );
            dialog.open();
        }
        return null; // must return null by IHandler contract
    }

    private IJavaElement extractSelectedMethod( ExecutionEvent event, IEditorPart editor ) {
        IEditorInput editorInput = editor.getEditorInput();
        EditorInputAnalysis analysis = new JDTWorkspaceEditorInputAnalysis( editorInput );
        return analysis.getSelectedMethod( getCurrentSelection( event ) );
    }
}
