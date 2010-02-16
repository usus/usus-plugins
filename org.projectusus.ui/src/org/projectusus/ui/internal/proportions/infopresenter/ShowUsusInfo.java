// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import static org.eclipse.ui.handlers.HandlerUtil.getActiveEditor;
import static org.eclipse.ui.handlers.HandlerUtil.getActiveShell;
import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.UsusInfoBuilder;
import org.projectusus.ui.internal.selection.EditorInputAnalysis;
import org.projectusus.ui.internal.selection.JDTWorkspaceEditorInputAnalysis;
import org.projectusus.ui.internal.viewer.LightweightDialog;

public class ShowUsusInfo extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        IEditorPart activeEditor = getActiveEditor( event );
        if( activeEditor != null ) {
            IMethod method = extractSelectedMethod( event, activeEditor );
            IUsusInfo ususInfo = new UsusInfoBuilder( method ).create();
            if( ususInfo != null ) {
                openLightWeightDialog( ususInfo, getActiveShell( event ) );
            }
        }
        return null; // must return null by IHandler contract
    }

    private void openLightWeightDialog( IUsusInfo ususInfo, Shell shell ) {
        LightweightDialog dialog = new UsusInfoLightweightDialog( shell );
        dialog.setInput( ususInfo );
        dialog.open();
    }

    private IMethod extractSelectedMethod( ExecutionEvent event, IEditorPart editor ) {
        IEditorInput editorInput = editor.getEditorInput();
        EditorInputAnalysis analysis = new JDTWorkspaceEditorInputAnalysis( editorInput );
        return analysis.getSelectedMethod( getCurrentSelection( event ) );
    }
}
