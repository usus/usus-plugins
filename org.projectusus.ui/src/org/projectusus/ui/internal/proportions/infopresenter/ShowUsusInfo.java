// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter;

import static org.eclipse.ui.handlers.HandlerUtil.getActiveEditor;
import static org.eclipse.ui.handlers.HandlerUtil.getActiveShell;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.UsusInfoBuilder;
import org.projectusus.ui.internal.selection.EditorInputAnalysis;
import org.projectusus.ui.internal.selection.JDTWorkspaceEditorInputAnalysis;

public class ShowUsusInfo extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        IEditorPart activeEditor = getActiveEditor( event );
        if( activeEditor != null ) {
            IJavaElement element = getSelectedJavaElement( activeEditor );
            IUsusInfo ususInfo = UsusInfoBuilder.of( element );
            if( ususInfo != null ) {
                openLightWeightDialog( ususInfo, getActiveShell( event ) );
            }
        }
        return null; // must return null by IHandler contract
    }

    private IJavaElement getSelectedJavaElement( IEditorPart activeEditor ) {
        EditorInputAnalysis analysis = new JDTWorkspaceEditorInputAnalysis( activeEditor.getEditorInput() );
        return analysis.getSelectedElement( calcCurrentSelection() );
    }

    private void openLightWeightDialog( IUsusInfo ususInfo, Shell shell ) {
        new UsusInfoDialog( shell, ususInfo ).open();
    }

    private ISelection calcCurrentSelection() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
    }
}
